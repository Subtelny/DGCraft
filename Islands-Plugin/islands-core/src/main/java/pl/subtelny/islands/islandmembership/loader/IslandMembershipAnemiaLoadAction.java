package pl.subtelny.islands.islandmembership.loader;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandMembership;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.repository.LoadAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IslandMembershipAnemiaLoadAction implements LoadAction<IslandMembershipAnemia> {

    private final Configuration configuration;

    private final IslandMembershipLoadRequest request;

    public IslandMembershipAnemiaLoadAction(Configuration configuration, IslandMembershipLoadRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public IslandMembershipAnemia perform() {
        return DSL.using(configuration)
                .select()
                .from(IslandMembership.ISLAND_MEMBERSHIP)
                .leftOuterJoin(Islands.ISLANDS)
                .on(IslandMembership.ISLAND_MEMBERSHIP.ISLAND_ID.eq(Islands.ISLANDS.ID))
                .where(getWhereConditions())
                .fetchOne(this::mapToAnemia);
    }

    @Override
    public List<IslandMembershipAnemia> performList() {
        return DSL.using(configuration)
                .selectFrom(IslandMembership.ISLAND_MEMBERSHIP)
                .where(getWhereConditions())
                .fetch(this::mapToAnemia);
    }

    private List<Condition> getWhereConditions() {
        List<Condition> conditions = new ArrayList<>();
        Optional<IslandMemberId> islandMemberIdOpt = request.getIslandMemberId();
        islandMemberIdOpt.ifPresent(islandMemberId -> conditions.add(IslandMembership.ISLAND_MEMBERSHIP.ISLAND_MEMBER_ID.eq(islandMemberId.toString())));

        Optional<IslandId> islandIdOpt = request.getIslandId();
        islandIdOpt.ifPresent(islandId -> conditions.add(IslandMembership.ISLAND_MEMBERSHIP.ISLAND_ID.eq(islandId.getId())));
        return conditions;
    }

    private IslandMembershipAnemia mapToAnemia(Record record) {

        return null;
    }

}
