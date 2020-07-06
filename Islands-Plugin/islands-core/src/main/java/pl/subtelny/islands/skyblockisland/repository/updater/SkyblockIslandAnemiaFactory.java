package pl.subtelny.islands.skyblockisland.repository.updater;

import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;

public class SkyblockIslandAnemiaFactory {

    public static SkyblockIslandAnemia toAnemia(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia();
        fillAnemiaBasicFields(island, anemia);
        anemia.setExtendLevel(island.getExtendLevel());
        anemia.setIslandCoordinates(island.getIslandCoordinates());
        anemia.setOwner(island.getOwner().getIslanderId());
        anemia.setPoints(island.getPoints());
        return anemia;
    }

    private static void fillAnemiaBasicFields(Island island, IslandAnemia islandAnemia) {
        islandAnemia.setIslandId(islandAnemia.getIslandId());
        islandAnemia.setCreatedDate(island.getCreatedDate());
        islandAnemia.setSpawn(island.getSpawn());
    }

}
