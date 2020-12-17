package pl.subtelny.utilities.configuration;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private final Map<ConfigurationKey, ConfigurationValue<?>> configuration = new HashMap<>();

    @Nullable
    public <T extends Serializable> T getValue(ConfigurationKey key) {
        ConfigurationValue<?> configurationValue = configuration.get(key);
        if (configurationValue != null) {
            return (T) configurationValue.get();
        }
        return null;
    }

    public void updateValue(ConfigurationKey key, ConfigurationValue value) {
        configuration.put(key, value);
    }

}
