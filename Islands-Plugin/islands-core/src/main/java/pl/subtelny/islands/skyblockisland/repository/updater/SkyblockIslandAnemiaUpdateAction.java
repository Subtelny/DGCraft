package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.SkyblockIslands;
import pl.subtelny.generated.tables.tables.records.SkyblockIslandsRecord;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.repository.island.updater.IslandAnemiaUpdateAction;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SkyblockIslandAnemiaUpdateAction extends IslandAnemiaUpdateAction<SkyblockIslandAnemia> {

    public SkyblockIslandAnemiaUpdateAction(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void perform(SkyblockIslandAnemia islandAnemia) {
        DSL.using(configuration).batch(
                getIslandRecordStatement(islandAnemia),
                getSkyblockIslandRecordStatement(islandAnemia)
        ).execute();
    }

    @Override
    public CompletionStage<Integer> performAsync(SkyblockIslandAnemia skyblockIslandAnemia) {
        CompletionStage<Integer> skyblock = getSkyblockIslandRecordStatement(skyblockIslandAnemia).executeAsync();
        CompletionStage<Integer> island = getIslandRecordStatement(skyblockIslandAnemia).executeAsync();
        return CompletableFuture.allOf(
                skyblock.toCompletableFuture(),
                island.toCompletableFuture())
                .thenApply(aVoid -> 1);
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
