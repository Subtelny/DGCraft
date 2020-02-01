package pl.subtelny.islands.repository.island.updater;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.core.generated.tables.records.SkyblockIslandsRecord;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.repository.island.anemia.SkyblockIslandAnemia;

public class SkyblockIslandAnemiaUpdateAction extends IslandAnemiaUpdateAction<SkyblockIslandAnemia> {

    public SkyblockIslandAnemiaUpdateAction(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void saveBasedIslandAnemia(SkyblockIslandAnemia islandAnemia) {
        SkyblockIslandsRecord record = DSL.using(configuration).newRecord(SkyblockIslands.SKYBLOCK_ISLANDS);
        record.setIslandId(islandAnemia.getIslandId().getId());
        record.setExtendLevel(islandAnemia.getExtendLevel());
        record.setOwner(islandAnemia.getOwner().getId());
        record.setPoints(islandAnemia.getPoints());

        IslandCoordinates islandCoordinates = islandAnemia.getIslandCoordinates();
        record.setX(islandCoordinates.getX());
        record.setZ(islandCoordinates.getZ());

        record.store();
    }

}
