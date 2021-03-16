package pl.subtelny.islands.island;

import pl.subtelny.utilities.configuration.datatype.BooleanDataType;
import pl.subtelny.utilities.configuration.datatype.DataType;

public enum IslandConfigType {

    SPAWNERS_DISABLED("SPAWNERS_DISABLED", BooleanDataType.TYPE),
    SPAWN_AGGRESSIVE_MOB_DISABLED("SPAWN_AGGRESSIVE_MOB_DISABLED", BooleanDataType.TYPE),
    SPAWN_PASSIVE_MOB_DISABLED("SPAWN_PASSIVE_MOB_DISABLED", BooleanDataType.TYPE);

    private final String key;

    private final DataType dataType;

    IslandConfigType(String key, DataType dataType) {
        this.key = key;
        this.dataType = dataType;
    }

    public String getKey() {
        return key;
    }

    public DataType getDataType() {
        return dataType;
    }
}
