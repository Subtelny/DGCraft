package pl.subtelny.core.city.repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FileCityRepository implements CityRepository {

    private static final String CITIES_FILE_NAME = "cities.yml";

    private File file;

    private Map<CityId, City> cities = new HashMap<>();

    public void initializeFile(Plugin plugin) {
        file = FileUtil.copyFile(plugin, CITIES_FILE_NAME);
    }

    public void loadCitiesFromFile() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection citiesSection = config.getConfigurationSection("city");
        if (citiesSection != null) {
            CityFileParserStrategy parser = new CityFileParserStrategy(file);
            cities = citiesSection.getKeys(false).stream()
                    .map(cityKey -> parser.load("city." + cityKey))
                    .collect(Collectors.toMap(City::getCityId, city -> city));
        }
    }

    @Override
    public Optional<City> find(CityId cityId) {
        return Optional.ofNullable(cities.get(cityId));
    }

    @Override
    public void save(City city) {
        Preconditions.checkNotNull(city, "City cannot be null");
        cities.put(city.getCityId(), city);
        new CityFileParserStrategy(file).set(getPath(city), city).save();
    }

    @Override
    public Set<City> getAll() {
        return Sets.newHashSet(cities.values());
    }

    private String getPath(City city) {
        return "city." + city.getCityId().getInternal();
    }

}
