package pl.subtelny.islands.skyblockisland.repository;


import pl.subtelny.islands.island.IslandId;

public class SkyblockIslandId extends IslandId {

    private static final String SKYBLOCK_ISLAND = "SKYBLOCK_ISLAND";

    public SkyblockIslandId(Integer islandId) {
        super(SKYBLOCK_ISLAND, islandId);
    }

    public static SkyblockIslandId of(Integer islandId) {
        return new SkyblockIslandId(islandId);
    }
}
