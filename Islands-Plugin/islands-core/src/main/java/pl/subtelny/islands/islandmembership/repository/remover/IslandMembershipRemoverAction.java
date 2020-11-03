package pl.subtelny.islands.islandmembership.repository.remover;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DeleteConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;
import pl.subtelny.core.api.repository.RemoveAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IslandMembershipRemoverAction implements RemoveAction<IslandMembershipRemoveRequest> {

    private final Configuration configuration;

    public IslandMembershipRemoverAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void perform(IslandMembershipRemoveRequest request) {
        getStatement(request).execute();
    }

    @Override
    public CompletableFuture<Integer> performAsync(IslandMembershipRemoveRequest request) {
        return getStatement(request)
                .executeAsync()
                .toCompletableFuture();
    }

    private DeleteConditionStep<IslandMembershipsRecord> getStatement(IslandMembershipRemoveRequest request) {
        return DSL.using(configuration)
                .deleteFrom(IslandMemberships.ISLAND_MEMBERSHIPS)
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
