package pl.subtelny.islands.module.skyblock.repository;

import org.jooq.DSLContext;
import org.jooq.Record;
import pl.subtelny.core.api.repository.LoadAction;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.api.IslandType;

import java.util.List;

public class SkyblockIslandCoordinatesLoadAction implements LoadAction<IslandCoordinates> {

    private final IslandType islandType;

    private final DSLContext connection;

    public SkyblockIslandCoordinatesLoadAction(IslandType islandType, DSLContext connection) {
        this.islandType = islandType;
        this.connection = connection;
    }

    @Override
    public IslandCoordinates perform() {
        return connection.select(SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z)
                .from(SkyblockIslands.SKYBLOCK_ISLANDS)
                .join(Islands.ISLANDS)
                .on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))
                .where(Islands.ISLANDS.TYPE.eq(islandType.getInternal()))
                .fetchOne(this::toIslandCoordinates);
    }

    @Override
    public List<IslandCoordinates> performList() {
        return connection.select(SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z)
                .from(SkyblockIslands.SKYBLOCK_ISLANDS)
                .join(Islands.ISLANDS)
                .on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))
                .where(Islands.ISLANDS.TYPE.contains(islandType.getInternal()))
                .fetch(this::toIslandCoordinates);
    }

    private IslandCoordinates toIslandCoordinates(Record record) {
        Integer x = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.X);
        Integer z = record.get(SkyblockIslands.SKYBLOCK_ISLANDS.Z);
        return new IslandCoordinates(x, z);
    }
}
