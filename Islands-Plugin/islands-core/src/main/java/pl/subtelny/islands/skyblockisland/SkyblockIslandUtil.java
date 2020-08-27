package pl.subtelny.islands.skyblockisland;

import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberRank;

public final class SkyblockIslandUtil {

    public static final IslandMemberRank RANK_OWNER = new IslandMemberRank("SKYBLOCK_ISLAND_OWNER", true);

    private static final String SKYBLOCK_ISLAND_ID_VALUE = "SKYBLOCK_ISLAND";

    public static IslandId id(Integer id) {
        return new IslandId(SKYBLOCK_ISLAND_ID_VALUE, id);
    }

}
