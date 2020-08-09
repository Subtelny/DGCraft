package pl.subtelny.islands.islander.repository.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.generated.tables.tables.records.IslandersRecord;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.repository.UpdateAction;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class IslanderAnemiaUpdateAction implements UpdateAction<IslanderAnemia, IslanderId> {

    private final Configuration configuration;

    public IslanderAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public IslanderId perform(IslanderAnemia islanderAnemia) {
        IslanderId islanderId = islanderAnemia.getIslanderId();
        if (islanderId.getInternal() == null) {
            return executeMissingId(islanderAnemia);
        }
        createIslanderStatement(islanderAnemia).execute();
        return islanderId;
    }

    @Override
    public CompletableFuture<IslanderId> performAsync(IslanderAnemia islanderAnemia) {
        IslanderId islanderId = islanderAnemia.getIslanderId();
        if (islanderId.getInternal() == null) {
            return JobsProvider.supplyAsync(() -> executeMissingId(islanderAnemia));
        }
        return createIslanderStatement(islanderAnemia).executeAsync()
                .toCompletableFuture()
                .thenApply(integer -> islanderId);
    }

    private InsertOnDuplicateSetMoreStep<IslandersRecord> createIslanderStatement(IslanderAnemia islanderAnemia) {
        IslandersRecord islanderRecord = createIslanderRecord(islanderAnemia);
        return DSL.using(configuration)
                .insertInto(Islanders.ISLANDERS)
                .set(islanderRecord)
                .onDuplicateKeyUpdate()
                .set(islanderRecord);
    }

    private IslanderId executeMissingId(IslanderAnemia islanderAnemia) {
        Record1<UUID> record = createIslanderStatement(islanderAnemia).returningResult(Islanders.ISLANDERS.ID).fetchOne();
        return IslanderId.of(record.component1());
    }

    private IslandersRecord createIslanderRecord(IslanderAnemia islanderAnemia) {
        IslandersRecord islandersRecord = new IslandersRecord();
        islandersRecord.setId(islanderAnemia.getIslanderId().getInternal());
        return islandersRecord;
    }
}
