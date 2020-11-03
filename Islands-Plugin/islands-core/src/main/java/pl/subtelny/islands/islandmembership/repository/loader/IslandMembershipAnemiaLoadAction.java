package pl.subtelny.islands.islandmembership.repository.loader;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.islandmembership.repository.anemia.IslandMembershipAnemia;
import pl.subtelny.core.api.repository.LoadAction;

import java.util.List;
import java.util.Optional;

public class IslandMembershipAnemiaLoadAction implements LoadAction<IslandMembershipAnemia> {

    private final IslandMembershipLoadRequest request;

    private final Configuration configuration;

    public IslandMembershipAnemiaLoadAction(IslandMembershipLoadRequest request, Configuration configuration) {
        this.request = request;
        this.configuration = configuration;
    }

    @Override
    public IslandMembershipAnemia perform() {
        return DSL.using(configuration)
                .select()
                .from(IslandMemberships.ISLAND_MEMBERSHIPS)
                .where(whereConditions())
                .fetchOne(this::mapIntoAnemia);
    }

    @Override
    public List<IslandMembershipAnemia> performList() {
        return DSL.using(configuration)
                .select()
                .from(IslandMemberships.ISLAND_MEMBERSHIPS)
                .where(whereConditions())
                .fetch(this::mapIntoAnemia);
    }

    private List<Condition> whereConditions() {
        List<Condition> conditions = Lists.newArrayList();
        Optional<IslandId> islanderIdOpt = request.getIslandId();
        islanderIdOpt.ifPresent(islandId -> conditions.add(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID.eq(islandId.getInternal())));

        Optional<IslandMemberId> islandMemberIdOpt = request.getIslandMemberId();
        islandMemberIdOpt.ifPresent(islandMemberId -> conditions.add(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID.eq(islandMemberId.getInternal())));
        return conditions;
    }

    private IslandMembershipAnemia mapIntoAnemia(Record record) {
        Integer islandId = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID);
        String rawIslandMemberId = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID);
        Boolean owner = record.get(IslandMemberships.ISLAND_MEMBERSHIPS.OWNER);
        return new IslandMembershipAnemia(islandId, rawIslandMemberId, owner);
    }
}
