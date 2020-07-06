package pl.subtelny.islands.repository.island.anemia;

import pl.subtelny.islands.model.guild.GuildIsland;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;

public final class IslandAnemiaFactory {

    public static IslandAnemia toAnemia(Island island) {
        IslandType islandType = island.getIslandType();
        switch (islandType) {
            case SKYBLOCK:
                return toAnemia((SkyblockIsland) island);
            case GUILD:
                return toAnemia((GuildIsland) island);
            default:
                throw new IllegalArgumentException("Not found factory for island type " + islandType);
        }
    }

    public static SkyblockIslandAnemia toAnemia(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia();
        fillAnemiaBasicFields(island, anemia);
        anemia.setExtendLevel(island.getExtendLevel());
        anemia.setIslandCoordinates(island.getIslandCoordinates());
        anemia.setPoints(island.getPoints());
        return anemia;
    }

    public static GuildIslandAnemia toAnemia(GuildIsland island) {
        GuildIslandAnemia anemia = new GuildIslandAnemia();
        fillAnemiaBasicFields(island, anemia);
        anemia.setCuboid(island.getCuboid());

        //TODO
        //fill other
        return anemia;
    }

    private static void fillAnemiaBasicFields(Island island, IslandAnemia islandAnemia) {
        islandAnemia.setIslandId(islandAnemia.getIslandId());
        islandAnemia.setCreatedDate(island.getCreatedDate());
        islandAnemia.setSpawn(island.getSpawn());
    }

}
