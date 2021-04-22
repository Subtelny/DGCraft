package pl.subtelny.islands.island.flags;


import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;

import java.util.Optional;

public class IslandFlag {

    private final String key;

    private final boolean defaultValue;

    public IslandFlag(String key, boolean defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public static IslandFlag of(String key, boolean defaultValue) {
        return new IslandFlag(key, defaultValue);
    }

    public String getKey() {
        return key;
    }

    public boolean getValue(Configuration configuration) {
        Optional<Boolean> value = configuration.findValue(key, BooleanDataType.TYPE);
        return value.orElse(defaultValue);
    }

    public void updateValue(Configuration configuration, boolean value) {
        if (defaultValue == value) {
            configuration.removeValue(key);
        } else {
            configuration.updateValue(key, value + "");
        }
    }
}
