package pl.subtelny.islands.island.membership.repository.update;

import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetMoreStep;
import pl.subtelny.core.api.repository.UpdateAction;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;
import pl.subtelny.islands.island.membership.repository.IslandMembershipAnemia;

public class IslandMembershipUpdateAction implements UpdateAction<IslandMembershipUpdateRequest, Integer> {

    private final DSLContext connection;

    public IslandMembershipUpdateAction(DSLContext connection) {
        this.connection = connection;
    }

    @Override
    public Integer perform(IslandMembershipUpdateRequest request) {
        return getStatement(request).execute();
    }

    private InsertOnDuplicateSetMoreStep<IslandMembershipsRecord> getStatement(IslandMembershipUpdateRequest request) {
        IslandMembershipAnemia anemia = toAnemia(request);
        IslandMembershipsRecord record = createRecord(anemia);
        return connection.insertInto(IslandMemberships.ISLAND_MEMBERSHIPS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record);
    }

    private IslandMembershipAnemia toAnemia(IslandMembershipUpdateRequest request) {
        return new IslandMembershipAnemia(request.getIslandId(), request.getIslandMemberId(), request.isOwner());
    }

    private IslandMembershipsRecord createRecord(IslandMembershipAnemia request) {
        IslandMembershipsRecord record = connection.newRecord(IslandMemberships.ISLAND_MEMBERSHIPS);
        record.setIslandId(request.getIslandId().getInternal());
        record.setIslandMemberId(request.getIslandMemberId().getInternal());
        record.setOwner(request.isOwner());
        return record;
    }
}
