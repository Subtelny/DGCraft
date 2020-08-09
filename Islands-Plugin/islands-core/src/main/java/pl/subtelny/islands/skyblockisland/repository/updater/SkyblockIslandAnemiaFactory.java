package pl.subtelny.islands.skyblockisland.repository.updater;

import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;

public class SkyblockIslandAnemiaFactory {

    public static SkyblockIslandAnemia toAnemia(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia();
        anemia.setIslandId(island.getIslandId());
        anemia.setCreatedDate(island.getCreatedDate());
        anemia.setSpawn(island.getSpawn());
        anemia.setExtendLevel(island.getExtendLevel());
        anemia.setIslandCoordinates(island.getIslandCoordinates());
        anemia.setPoints(island.getPoints());
        return anemia;
    }

}
