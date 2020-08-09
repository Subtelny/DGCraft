package pl.subtelny.islands.skyblockisland.islandmembership.updater;

import org.jooq.Configuration;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.enums.Membershiptype;
import pl.subtelny.generated.tables.tables.IslandsMembership;
import pl.subtelny.generated.tables.tables.records.IslandsMembershipRecord;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.repository.UpdateAction;

import java.util.concurrent.CompletableFuture;

public class IslandMembershipAnemiaUpdateAction implements UpdateAction<IslandMembershipAnemia, IslanderId> {

    private final Configuration configuration;

    public IslandMembershipAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public IslanderId perform(IslandMembershipAnemia islandMembershipAnemia) {
        getIslandMembershipStatement(islandMembershipAnemia).execute();
        return islandMembershipAnemia.getIslanderId();
    }

    @Override
    public CompletableFuture<IslanderId> performAsync(IslandMembershipAnemia islandMembershipAnemia) {
        return getIslandMembershipStatement(islandMembershipAnemia).executeAsync()
                .toCompletableFuture()
                .thenApply(integer -> islandMembershipAnemia.getIslanderId());
    }

    private InsertOnDuplicateSetMoreStep<IslandsMembershipRecord> getIslandMembershipStatement(IslandMembershipAnemia anemia) {
        IslandsMembershipRecord record = createRecord(anemia);
        return DSL.using(configuration)
                .insertInto(IslandsMembership.ISLANDS_MEMBERSHIP)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private IslandsMembershipRecord createRecord(IslandMembershipAnemia anemia) {
        IslandsMembershipRecord record = DSL.using(configuration).newRecord(IslandsMembership.ISLANDS_MEMBERSHIP);
        record.setIslanderId(anemia.getIslanderId().getInternal());
        record.setIslandId(anemia.getIslandId().getId());
        record.setMembershipType(Membershiptype.valueOf(anemia.getMembershipType().name()));
        return record;
    }
}
