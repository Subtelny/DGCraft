package pl.subtelny.islands.api;

import pl.subtelny.islands.api.flags.IslandFlag;
import pl.subtelny.islands.api.flags.IslandFlags;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;

import java.util.Map;
import java.util.Optional;

public class IslandConfiguration extends Configuration {

    public IslandConfiguration(Map<String, String> fields) {
        super(fields);
    }

    public IslandConfiguration() {

    }

    public boolean getValue(String key) {
        return super.findValue(key)
                .map(Boolean::parseBoolean)
                .orElseGet(() -> IslandFlags.findIslandFlag(key)
                        .map(IslandFlag::getDefaultValue)
                        .orElseGet(BooleanDataType.TYPE::getDefault));
    }

    @Override
    public Optional<String> findValue(String key) {
        return Optional.of(getValue(key) + "");
    }

    @Override
    public void updateValue(String key, String value) {
        IslandFlags.findIslandFlag(key)
                .ifPresentOrElse(
                        islandFlag -> handleUpdateIslandFlagValue(islandFlag, value),
                        () -> handleUpdateValue(key, value));
    }

    private <T> void handleUpdateValue(String key, String value) {
        if (BooleanDataType.TYPE.isDefault(value)) {
            removeValue(key);
        } else {
            super.updateValue(key, value);
        }
    }

    private void handleUpdateIslandFlagValue(IslandFlag islandFlag, String value) {
        boolean booleanValue = Boolean.parseBoolean(value);
        boolean defaultValue = islandFlag.getDefaultValue();
        if (defaultValue == booleanValue) {
            removeValue(islandFlag.getKey());
        } else {
            super.updateValue(islandFlag.getKey(), value);
        }
    }

}
