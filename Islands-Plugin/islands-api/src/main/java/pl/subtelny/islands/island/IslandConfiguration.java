package pl.subtelny.islands.island;

import pl.subtelny.islands.island.flags.IslandFlag;
import pl.subtelny.islands.island.flags.IslandFlags;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.configuration.datatype.DataType;

import java.util.Map;
import java.util.Optional;

public class IslandConfiguration extends Configuration {

    public IslandConfiguration(Map<String, String> fields) {
        super(fields);
    }

    public IslandConfiguration() {

    }

    public <T> String getStringValue(String key, DataType<T> dataType) {
        Optional<String> valueOpt = findValue(key);
        return valueOpt.orElseGet(() -> IslandFlags.findIslandFlag(key)
                .map(islandFlag -> islandFlag.getValue(this) + "")
                .orElseGet(() -> dataType.getDefault().toString()));
    }

    public <T> T getValue(String key, DataType<T> dataType) {
        return findValue(key, dataType)
                .orElseGet(dataType::getDefault);
    }

    public <T> void updateValue(String key, DataType<T> dataType, String value) {
        IslandFlags.findIslandFlag(key)
                .ifPresentOrElse(
                        islandFlag -> handleUpdateIslandFlagValue(islandFlag, value),
                        () -> handleUpdateValue(key, dataType, value));
    }

    private <T> void handleUpdateValue(String key, DataType<T> dataType, String value) {
        if (dataType.isDefault(value)) {
            removeValue(key);
        } else {
            updateValue(key, value);
        }
    }

    private void handleUpdateIslandFlagValue(IslandFlag islandFlag, String value) {
        boolean booleanValue = Boolean.parseBoolean(value);
        islandFlag.updateValue(this, booleanValue);
    }

}
