package pl.subtelny.islands.island.repository;

import org.jooq.DSLContext;
import org.jooq.DeleteConditionStep;
import org.jooq.Query;
import pl.subtelny.core.api.repository.RemoveAction;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.IslandsRecord;
import pl.subtelny.generated.tables.tables.records.SkyblockIslandsRecord;
import pl.subtelny.islands.island.IslandId;

public class IslandRemoveAction implements RemoveAction<IslandId> {

    private final DSLContext connection;

    public IslandRemoveAction(DSLContext connection) {
        this.connection = connection;
    }

    @Override
    public void perform(IslandId islandId) {
        cascadeDeleteIsland(islandId);
    }

    private void cascadeDeleteIsland(IslandId islandId) {
        DeleteConditionStep<SkyblockIslandsRecord> deleteSkyblockIsland = getDeleteSkyblockIsland(islandId);
        DeleteConditionStep<IslandsRecord> deleteIsland = getDeleteIsland(islandId);
        execute(deleteSkyblockIsland, deleteIsland);
    }

    private void execute(Query deleteSkyblockIsland, Query deleteIsland) {
        connection.batch(deleteSkyblockIsland, deleteIsland).execute();
    }

    private DeleteConditionStep<IslandsRecord> getDeleteIsland(IslandId islandId) {
        return connection.deleteFrom(Islands.ISLANDS).
                where(Islands.ISLANDS.ID.eq(islandId.getId()));
    }

    private DeleteConditionStep<SkyblockIslandsRecord> getDeleteSkyblockIsland(IslandId islandId) {
        return connection.deleteFrom(SkyblockIslands.SKYBLOCK_ISLANDS).
                where(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID.eq(islandId.getId()));
    }

}
