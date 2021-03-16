package pl.subtelny.islands.island;

import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.configuration.datatype.DataType;

public class IslandConfiguration {

    private final Configuration configuration;

    public IslandConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> String getStringValue(String key, DataType<T> dataType) {
        return dataType.convertToString(getValue(key, dataType));
    }

    public <T> T getValue(String key, DataType<T> dataType) {
        return configuration.findValue(key, dataType)
                .orElseGet(dataType::getDefault);
    }

    public <T> void updateValue(String key, DataType<T> dataType, String value) {
        if (dataType.isDefault(value)) {
            configuration.removeValue(key);
        } else {
            configuration.updateValue(key, value);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
