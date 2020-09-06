package pl.subtelny.islands.skyblockisland;

import pl.subtelny.islands.island.IslandId;

public final class SkyblockIslandUtil {

    private static final String SKYBLOCK_ISLAND_ID_VALUE = "SKYBLOCK_ISLAND";

    public static IslandId id(Integer id) {
        return new IslandId(SKYBLOCK_ISLAND_ID_VALUE, id);
    }

}
