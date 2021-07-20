package pl.subtelny.islands.api.flags;


import pl.subtelny.islands.api.IslandConfiguration;
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

    public boolean getValue(IslandConfiguration configuration) {
        Optional<Boolean> value = configuration.findValue(key, BooleanDataType.TYPE);
        return value.orElse(defaultValue);
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

}
