package pl.subtelny.islands.islandmembership.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandMembership;
import pl.subtelny.generated.tables.tables.records.IslandMembershipRecord;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.repository.UpdateAction;

import java.util.concurrent.CompletableFuture;

public class IslandMembershipAnemiaUpdateAction implements UpdateAction<IslandMembershipAnemia, IslandMemberId> {

    private final Configuration configuration;

    public IslandMembershipAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public IslandMemberId perform(IslandMembershipAnemia islandMembershipAnemia) {
        getIslandMembershipStatement(islandMembershipAnemia).execute();
        return islandMembershipAnemia.getIslandMemberId();
    }

    @Override
    public CompletableFuture<IslandMemberId> performAsync(IslandMembershipAnemia islandMembershipAnemia) {
        return getIslandMembershipStatement(islandMembershipAnemia).executeAsync()
                .toCompletableFuture()
                .thenApply(integer -> islandMembershipAnemia.getIslandMemberId());
    }

    private InsertOnDuplicateSetMoreStep<IslandMembershipRecord> getIslandMembershipStatement(IslandMembershipAnemia anemia) {
        IslandMembershipRecord record = createRecord(anemia);
        return DSL.using(configuration)
                .insertInto(IslandMembership.ISLAND_MEMBERSHIP)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private IslandMembershipRecord createRecord(IslandMembershipAnemia anemia) {
        IslandMembershipRecord record = DSL.using(configuration).newRecord(IslandMembership.ISLAND_MEMBERSHIP);
        record.setIslandMemberId(anemia.getIslandMemberId().toString());
        record.setIslandId(anemia.getIslandId().getId());
        record.setRank(anemia.getRank().getInternal());
        return record;
    }
}
