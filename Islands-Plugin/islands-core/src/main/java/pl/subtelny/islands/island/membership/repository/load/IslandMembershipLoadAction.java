package pl.subtelny.islands.island.membership.repository.load;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import pl.subtelny.core.api.repository.LoadAction;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.membership.repository.IslandMembershipAnemia;

import java.util.ArrayList;
import java.util.List;

public class IslandMembershipLoadAction implements LoadAction<IslandMembershipAnemia> {

    private final DSLContext connection;

    private final IslandMembershipLoadRequest request;

    public IslandMembershipLoadAction(DSLContext connection, IslandMembershipLoadRequest request) {
        this.connection = connection;
        this.request = request;
    }

    @Override
    public IslandMembershipAnemia perform() {
        return connection.select()
                .from(IslandMemberships.ISLAND_MEMBERSHIPS)
                .where(getWhereConditions())
                .fetchOne(this::mapIntoAnemia);
    }

    @Override
    public List<IslandMembershipAnemia> performList() {
        return connection.select()
                .from(IslandMemberships.ISLAND_MEMBERSHIPS)
                .where(getWhereConditions())
                .fetch(this::mapIntoAnemia);
    }

    private List<Condition> getWhereConditions() {
        List<Condition> conditions = new ArrayList<>();
        request.getIslandId().ifPresent(islandId -> conditions.add(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID.eq(islandId.getInternal())));
        request.getIslandMemberId().ifPresent(islandMemberId -> conditions.add(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID.eq(islandMemberId.getInternal())));
        return conditions;
    }

    private IslandMembershipAnemia mapIntoAnemia(Record record) {
        String islandIdRaw = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID);
        String islandMemberIdRaw = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID);
        Boolean isOwner = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.OWNER);
        IslandId islandId = IslandId.of(islandIdRaw);
        IslandMemberId islandMemberId = IslandMemberId.of(islandMemberIdRaw);
        return new IslandMembershipAnemia(islandId, islandMemberId, isOwner);
    }
}
