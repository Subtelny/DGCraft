package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.SkyblockIslandsRecord;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.island.repository.updater.IslandAnemiaUpdateAction;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.jobs.JobsProvider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SkyblockIslandAnemiaUpdateAction extends IslandAnemiaUpdateAction<SkyblockIslandAnemia, SkyblockIslandId> {

    public SkyblockIslandAnemiaUpdateAction(Configuration configuration) {
        super(configuration);
    }

    @Override
    public SkyblockIslandId perform(SkyblockIslandAnemia islandAnemia) {
        SkyblockIslandId islandId = islandAnemia.getIslandId();
        if (islandId.getId() == null) {
            return executeMissingId(islandAnemia);
        }
        DSL.using(configuration).batch(
                getIslandRecordStatement(islandAnemia),
                getSkyblockIslandRecordStatement(islandAnemia)
        ).execute();
        return islandId;
    }

    @Override
    public CompletableFuture<SkyblockIslandId> performAsync(SkyblockIslandAnemia skyblockIslandAnemia) {
        if (skyblockIslandAnemia.getIslandId().getId() == null) {
            return JobsProvider.supplyAsync(() -> executeMissingId(skyblockIslandAnemia));
        }
        CompletionStage<Integer> island = getIslandRecordStatement(skyblockIslandAnemia).executeAsync();
        CompletionStage<Integer> skyblock = getSkyblockIslandRecordStatement(skyblockIslandAnemia).executeAsync();
        return CompletableFuture.allOf(
                skyblock.toCompletableFuture(),
                island.toCompletableFuture())
                .thenApply(aVoid -> skyblockIslandAnemia.getIslandId());
    }

    public SkyblockIslandId executeMissingId(SkyblockIslandAnemia islandAnemia) {
        Record1<Integer> record = getIslandRecordStatement(islandAnemia).returningResult(Islands.ISLANDS.ID).fetchOne();
        SkyblockIslandId id = SkyblockIslandId.of(record.component1());
        islandAnemia.setIslandId(id);
        getSkyblockIslandRecordStatement(islandAnemia).execute();
        return id;
    }

    private InsertOnDuplicateSetMoreStep<SkyblockIslandsRecord> getSkyblockIslandRecordStatement(SkyblockIslandAnemia islandAnemia) {
        SkyblockIslandsRecord record = createRecord(islandAnemia);
        return DSL.using(configuration)
                .insertInto(SkyblockIslands.SKYBLOCK_ISLANDS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private SkyblockIslandsRecord createRecord(SkyblockIslandAnemia islandAnemia) {
        SkyblockIslandsRecord record = DSL.using(configuration).newRecord(SkyblockIslands.SKYBLOCK_ISLANDS);
        record.setIslandId(islandAnemia.getIslandId().getId());
        record.setExtendLevel(islandAnemia.getExtendLevel());
        record.setOwner(islandAnemia.getOwner().getId());
        record.setPoints(islandAnemia.getPoints());

        IslandCoordinates islandCoordinates = islandAnemia.getIslandCoordinates();
        record.setX(islandCoordinates.getX());
        record.setZ(islandCoordinates.getZ());
        return record;
    }
}
