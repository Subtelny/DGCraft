package pl.subtelny.utilities.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Configuration {

    private final Map<ConfigurationKey, ConfigurationValue<?>> configuration = new HashMap<>();

    public <T> Optional<T> findValue(ConfigurationKey key, Class<T> clazz) {
        ConfigurationValue<?> configurationValue = configuration.get(key);
        if (configurationValue != null) {
            T value = clazz.cast(configurationValue.get());
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public void updateValue(ConfigurationKey key, ConfigurationValue value) {
        configuration.put(key, value);
    }

    public Map<ConfigurationKey, ConfigurationValue<?>> getConfiguration() {
        return new HashMap<>(configuration);
    }
}
