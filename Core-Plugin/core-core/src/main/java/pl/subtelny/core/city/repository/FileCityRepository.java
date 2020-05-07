package pl.subtelny.core.city.repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.api.city.City;
import pl.subtelny.core.api.city.CityPortal;
import pl.subtelny.core.api.city.CityRepository;
import pl.subtelny.core.city.CityImpl;
import pl.subtelny.core.city.CityPortalCuboid;
import pl.subtelny.utilities.FileUtil;
import pl.subtelny.utilities.config.LocationFileParserStrategy;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FileCityRepository implements CityRepository {

    private static final String CITIES_FILE_NAME = "cities.yml";

    private File file;

    private Map<CityType, City> cities = new HashMap<>();

    public FileCityRepository() {
    }

    public void initializeFile(Plugin plugin) {
        file = FileUtil.copyFile(plugin, CITIES_FILE_NAME);
    }

    public void loadCitiesFromFile() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection citiesSection = config.getConfigurationSection("cities");
        if (citiesSection != null) {
            cities = citiesSection.getKeys(false).stream()
                    .map(cityKey -> mapConfigIntoCity(config, cityKey))
                    .collect(Collectors.toMap(City::getType, city -> city));
        }

    }

    private City mapConfigIntoCity(YamlConfiguration config, String rawCityType) {
        CityType cityType = CityType.valueOf(rawCityType);
        CityPortal cityPortal = mapConfigIntoCityPortal(config, "cities." + rawCityType);
        Location spawn = new LocationFileParserStrategy(file).load("cities." + rawCityType + ".spawn");
        return new CityImpl(cityPortal, spawn, cityType);
    }

    private CityPortal mapConfigIntoCityPortal(YamlConfiguration config, String cityPath) {
        LocationFileParserStrategy parser = new LocationFileParserStrategy(file);
        Location loc1 = parser.load( cityPath + ".portal.loc1");
        Location loc2 = parser.load( cityPath + ".portal.loc2");
        Location target = parser.load( cityPath + ".portal.target");
        Cuboid cuboid = new Cuboid(cityPath, loc1, loc2);
        return new CityPortalCuboid(cuboid, target);
    }

    @Override
    public City get(CityType cityType) {
        return cities.get(cityType);
    }

    @Override
    public void save(City city) {
        Preconditions.checkNotNull(city, "City cannot be null");
        cities.put(city.getType(), city);
    }

    @Override
    public Set<City> getAll() {
        return Sets.newHashSet(cities.values());
    }

}
