package pl.subtelny.islands.islandmembership.remover;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DeleteConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandMembership;
import pl.subtelny.generated.tables.tables.records.IslandMembershipRecord;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.repository.RemoverAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IslandMembershipRemoveAction implements RemoverAction<IslandMembershipRemoveRequest> {

    private final Configuration configuration;

    private final IslandMembershipRemoveRequest request;

    public IslandMembershipRemoveAction(Configuration configuration, IslandMembershipRemoveRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public void perform() {
        createStatement().execute();
    }

    @Override
    public CompletableFuture<Integer> performAsync() {
        return createStatement().executeAsync()
                .toCompletableFuture();
    }

    public DeleteConditionStep<IslandMembershipRecord> createStatement() {
        return DSL.using(configuration)
                .deleteFrom(IslandMembership.ISLAND_MEMBERSHIP)
                .where(whereConditions());
    }

    private List<Condition> whereConditions() {
        List<Condition> conditions = new ArrayList<>();
        List<IslandId> islandIds = request.getIslandIds();
        if (!islandIds.isEmpty()) {
            conditions.add(IslandMembership.ISLAND_MEMBERSHIP.ISLAND_ID.in(islandIds));
        }
        List<IslandMemberId> islanderIds = request.getIslandMemberIds();
        if (!islanderIds.isEmpty()) {
            conditions.add(IslandMembership.ISLAND_MEMBERSHIP.ISLAND_MEMBER_ID.in(islanderIds));
        }
        List<IslandMemberId> notInIslanderIds = request.getNotInIslandMemberIds();
        if (!notInIslanderIds.isEmpty()) {
            conditions.add(IslandMembership.ISLAND_MEMBERSHIP.ISLAND_MEMBER_ID.notIn(notInIslanderIds));
        }
        return conditions;
    }
}
