package pl.subtelny.islands.skyblockisland.repository;

import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslandType;

public class SkyblockIslandId extends IslandId {

    private static final IslandType type = IslandType.SKYBLOCK;

    public SkyblockIslandId(Integer islandId) {
        super(type, islandId);
    }

    public static SkyblockIslandId of(Integer islandId) {
        return new SkyblockIslandId(islandId);
    }
}
