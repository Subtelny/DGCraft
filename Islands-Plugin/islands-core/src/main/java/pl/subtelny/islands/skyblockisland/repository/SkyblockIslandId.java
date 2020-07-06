package pl.subtelny.islands.skyblockisland.repository;

import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.IslandType;

public class SkyblockIslandId extends IslandId {

    private static final IslandType type = IslandType.SKYBLOCK;

    public SkyblockIslandId(Integer islandId) {
        super(type, islandId);
    }
}
