package pl.subtelny.islands.islandmembership.repository.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;
import pl.subtelny.islands.islandmembership.repository.anemia.IslandMembershipAnemia;
import pl.subtelny.repository.UpdateAction;

import java.util.concurrent.CompletableFuture;

public class IslandMembershipUpdateAction implements UpdateAction<IslandMembershipAnemia, Integer> {

    private final Configuration configuration;

    public IslandMembershipUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Integer perform(IslandMembershipAnemia islandMembershipAnemia) {
        return getStatement(islandMembershipAnemia).execute();
    }

    @Override
    public CompletableFuture<Integer> performAsync(IslandMembershipAnemia islandMembershipAnemia) {
        return getStatement(islandMembershipAnemia)
                .executeAsync()
                .toCompletableFuture();
    }

    private InsertOnDuplicateSetMoreStep<IslandMembershipsRecord> getStatement(IslandMembershipAnemia anemia) {
        IslandMembershipsRecord record = createRecord(anemia);
        return DSL.using(configuration)
                .insertInto(IslandMemberships.ISLAND_MEMBERSHIPS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private IslandMembershipsRecord createRecord(IslandMembershipAnemia request) {
        IslandMembershipsRecord record = DSL.using(configuration).newRecord(IslandMemberships.ISLAND_MEMBERSHIPS);
        record.setIslandId(request.getIslandId());
        record.setIslandMemberId(request.getIslandMemberId());
        return record;
    }
}
