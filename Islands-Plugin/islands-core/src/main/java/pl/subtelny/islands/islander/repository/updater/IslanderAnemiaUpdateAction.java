package pl.subtelny.islands.islander.repository.updater;

import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.Record1;
import pl.subtelny.core.api.repository.UpdateAction;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.generated.tables.tables.records.IslandersRecord;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;

import java.util.UUID;

public class IslanderAnemiaUpdateAction implements UpdateAction<IslanderAnemia, IslanderId> {

    private final DSLContext connection;

    public IslanderAnemiaUpdateAction(DSLContext connection) {
        this.connection = connection;
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

    private InsertOnDuplicateSetMoreStep<IslandersRecord> createIslanderStatement(IslanderAnemia islanderAnemia) {
        IslandersRecord islanderRecord = createIslanderRecord(islanderAnemia);
        return connection.insertInto(Islanders.ISLANDERS)
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
