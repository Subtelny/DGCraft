package pl.subtelny.islands.module.skyblock.repository.anemia;

import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;

public class SkyblockIslandAnemiaFactory {

    public static SkyblockIslandAnemia toAnemia(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia();
        anemia.setIslandId(island.getId());
        anemia.setIslandType(island.getId().getIslandType());
        anemia.setCreatedDate(island.getCreationDate());
        anemia.setSpawn(island.getSpawn());
        anemia.setExtendLevel(island.getExtendLevel());
        anemia.setIslandCoordinates(island.getIslandCoordinates());
        anemia.setPoints(island.getPoints());
        return anemia;
    }

}
