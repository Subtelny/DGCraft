package pl.subtelny.core.city.repository;

import org.bukkit.Location;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.CityPortal;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.cuboid.CuboidFileParserStrategy;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;
import pl.subtelny.utilities.location.LocationFileParserStrategy;

import java.io.File;

public class CityFileParserStrategy extends AbstractFileParserStrategy<City> {

    protected CityFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public City load(String path) {
        return loadCity(path);
    }

    @Override
    public Saveable set(String path, City value) {
        new ObjectFileParserStrategy<String>(configuration, file).set(path + ".type", value.getCityId().getInternal());
        new LocationFileParserStrategy(configuration, file).set(path + ".spawn", value.getSpawn());
        new CuboidFileParserStrategy(configuration, file).set(path + ".cuboid", value.getCuboid());
        CityPortal cityPortal = value.getCityPortal();
        new LocationFileParserStrategy(configuration, file).set(path + ".portal.target", cityPortal.getTeleportTarget());
        new CuboidFileParserStrategy(configuration, file).set(path + ".portal.cuboid", cityPortal.getCuboid());
        return this;
    }

    private City loadCity(String path) {
        CityPortal cityPortal = loadCityPortal(path);
        Location spawn = new LocationFileParserStrategy(configuration, file).load(path + ".spawn");
        String rawCityType = new ObjectFileParserStrategy<String>(configuration, file).load(path + ".type");
        CityId cityId = CityId.of(rawCityType);
        Cuboid cuboid = loadCityCuboid(path);
        return new City(cityId, spawn, cuboid, cityPortal);
    }

    private CityPortal loadCityPortal(String path) {
        Cuboid cuboid = new CuboidFileParserStrategy(file).load(path + ".portal.cuboid");
        Location targetLocation = new LocationFileParserStrategy(file).load(path + ".portal.target");
        return new CityPortal(cuboid, targetLocation);
    }

    private Cuboid loadCityCuboid(String path) {
        return new CuboidFileParserStrategy(file).load(path + ".cuboid");
    }

}
