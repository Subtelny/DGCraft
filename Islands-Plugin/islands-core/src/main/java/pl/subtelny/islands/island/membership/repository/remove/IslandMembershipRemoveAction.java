package pl.subtelny.islands.island.membership.repository.remove;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DeleteConditionStep;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;
import pl.subtelny.core.api.repository.RemoveAction;

import java.util.ArrayList;
import java.util.List;

public class IslandMembershipRemoveAction implements RemoveAction<IslandMembershipRemoveRequest> {

    private final DSLContext connection;

    public IslandMembershipRemoveAction(DSLContext connection) {
        this.connection = connection;
    }

    @Override
    public void perform(IslandMembershipRemoveRequest request) {
        getStatement(request).execute();
    }

    private DeleteConditionStep<IslandMembershipsRecord> getStatement(IslandMembershipRemoveRequest request) {
        return connection.deleteFrom(IslandMemberships.ISLAND_MEMBERSHIPS)
                .where(getWhereConditions(request));
    }

    private List<Condition> getWhereConditions(IslandMembershipRemoveRequest request) {
        List<Condition> conditions = new ArrayList<>();
        if (request.getIslandId() != null) {
            conditions.add(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID.eq(request.getIslandId().getInternal()));
        }
        if (request.getIslandMemberId() != null) {
            conditions.add(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID.eq(request.getIslandMemberId().getInternal()));
        }
        return conditions;
    }

}
