package pl.subtelny.islands.skyblockisland.islandmembership.remover;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DeleteConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandsMembership;
import pl.subtelny.generated.tables.tables.records.IslandsMembershipRecord;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
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

    public DeleteConditionStep<IslandsMembershipRecord> createStatement() {
        return DSL.using(configuration)
                .deleteFrom(IslandsMembership.ISLANDS_MEMBERSHIP)
                .where(whereConditions());
    }

    private List<Condition> whereConditions() {
        List<Condition> conditions = new ArrayList<>();
        List<IslandId> islandIds = request.getIslandIds();
        if (!islandIds.isEmpty()) {
            conditions.add(IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID.in(islandIds));
        }
        List<IslanderId> islanderIds = request.getIslanderIds();
        if (!islanderIds.isEmpty()) {
            conditions.add(IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID.in(islanderIds));
        }
        List<MembershipType> membershipTypes = request.getMembershipTypes();
        if (!membershipTypes.isEmpty()) {
            conditions.add(IslandsMembership.ISLANDS_MEMBERSHIP.MEMBERSHIP_TYPE.in(membershipTypes));
        }
        return conditions;
    }
}
