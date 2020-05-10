package pl.subtelny.core.city.repository;

import org.bukkit.Location;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.api.city.City;
import pl.subtelny.core.api.city.CityPortal;
import pl.subtelny.core.city.CityImpl;
import pl.subtelny.core.city.CityPortalCuboid;
import pl.subtelny.utilities.config.*;
import pl.subtelny.utilities.cuboid.Cuboid;

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
        new EnumFileParserStrategy<CityType>(file).set(path + ".type", value.getType());
        new LocationFileParserStrategy(file).set(path + ".spawn", value.getSpawn());
        CityPortalCuboid cityPortal = (CityPortalCuboid) value.getCityPortal();
        new LocationFileParserStrategy(file).set(path + ".portal.target", cityPortal.getTeleportTarget());
        new CuboidFileParserStrategy(file).set(path + ".portal.cuboid", cityPortal.getCuboid());
        return this;
    }

    private City loadCity(String path) {
        CityPortal cityPortal = loadCityPortal(path);
        Location spawn = new LocationFileParserStrategy(file).load(path + ".spawn");
        CityType cityType = new EnumFileParserStrategy<CityType>(file).load(path + ".type");
        return new CityImpl(cityPortal, spawn, cityType);
    }

    private CityPortal loadCityPortal(String path) {
        Cuboid cuboid = new CuboidFileParserStrategy(file).load(path + ".portal.cuboid");
        Location targetLocation = new LocationFileParserStrategy(file).load(path + ".portal.target");
        return new CityPortalCuboid(cuboid, targetLocation);
    }

}
