package pl.subtelny.islands.module.skyblock.repository.anemia;

import org.jooq.DSLContext;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.SkyblockIslandsRecord;
import pl.subtelny.islands.api.repository.anemia.IslandAnemia;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;

public class SkyblockIslandAnemia {

    private IslandAnemia islandAnemia;

    private IslandCoordinates islandCoordinates;

    private int extendLevel;

    SkyblockIslandAnemia() {
    }

    public SkyblockIslandAnemia(IslandAnemia islandAnemia, IslandCoordinates islandCoordinates, int extendLevel) {
        this.islandAnemia = islandAnemia;
        this.islandCoordinates = islandCoordinates;
        this.extendLevel = extendLevel;
    }

    public SkyblockIslandAnemia(IslandAnemia islandAnemia, IslandCoordinates islandCoordinates) {
        this.islandAnemia = islandAnemia;
        this.islandCoordinates = islandCoordinates;
    }

	public IslandAnemia getIslandAnemia() {
		return islandAnemia;
	}

	public void setIslandAnemia(IslandAnemia islandAnemia) {
		this.islandAnemia = islandAnemia;
	}

	public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    public void setIslandCoordinates(IslandCoordinates islandCoordinates) {
        this.islandCoordinates = islandCoordinates;
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public void setExtendLevel(int extendLevel) {
        this.extendLevel = extendLevel;
    }

    public SkyblockIslandsRecord toRecord(DSLContext context) {
        SkyblockIslandsRecord record = context.newRecord(SkyblockIslands.SKYBLOCK_ISLANDS);
        record.setIslandId(getIslandAnemia().getIslandId().getId());
        record.setExtendLevel(getExtendLevel());
        IslandCoordinates islandCoordinates = getIslandCoordinates();
        record.setX(islandCoordinates.getX());
        record.setZ(islandCoordinates.getZ());
        return record;
    }

    public static SkyblockIslandAnemia toAnemia(SkyblockIsland island) {
        SkyblockIslandAnemia anemia = new SkyblockIslandAnemia();
        IslandAnemia islandAnemia = new IslandAnemia();
        islandAnemia.setIslandId(island.getId());
        islandAnemia.setIslandType(island.getId().getIslandType());
        islandAnemia.setCreatedDate(island.getCreationDate());
        islandAnemia.setSpawn(island.getSpawn());
        islandAnemia.setPoints(island.getPoints());
        anemia.setExtendLevel(island.getExtendLevel());
        anemia.setIslandCoordinates(island.getIslandCoordinates());
        anemia.setIslandAnemia(islandAnemia);
        return anemia;
    }

}
