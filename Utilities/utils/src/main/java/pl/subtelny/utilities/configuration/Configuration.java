package pl.subtelny.utilities.configuration;

import pl.subtelny.utilities.configuration.datatype.DataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Configuration {

    private final Map<String, String> configuration;

    public Configuration() {
        configuration = new HashMap<>();
    }

    public Configuration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public <T> Optional<T> findValue(String key, DataType<T> dataType) {
        return findValue(key)
                .map(dataType::convertToType);
    }

    public Optional<String> findValue(String key) {
        return Optional.ofNullable(configuration.get(key));
    }

    public Optional<String> findConfigurationValue(String key) {
        return Optional.ofNullable(configuration.get(key));
    }

    public void updateValue(String key, String value) {
        configuration.put(key, value);
    }

    public Map<String, String> asMap() {
        return new HashMap<>(configuration);
    }

    public void removeValue(String key) {
        configuration.remove(key);
    }
}
