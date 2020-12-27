package pl.subtelny.islands.island.skyblockisland.repository.anemia;

import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;

public class SkyblockIslandAnemiaFactory {

    public static SkyblockIslandAnemia toAnemia(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia();
        anemia.setIslandId(island.getId());
        anemia.setCreatedDate(island.getCreationDate());
        anemia.setSpawn(island.getSpawn());
        anemia.setExtendLevel(island.getExtendLevel());
        anemia.setIslandCoordinates(island.getIslandCoordinates());
        anemia.setPoints(island.getPoints());
        return anemia;
    }

}
