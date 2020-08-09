package pl.subtelny.islands.skyblockisland.islandmembership.loader;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.enums.Islandtype;
import pl.subtelny.generated.tables.enums.Membershiptype;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.IslandsMembership;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslandType;
import pl.subtelny.islands.skyblockisland.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
import pl.subtelny.repository.LoadAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .from(IslandsMembership.ISLANDS_MEMBERSHIP)
                .leftOuterJoin(Islands.ISLANDS)
                .on(IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID.eq(Islands.ISLANDS.ID))
                .where(getWhereConditions())
                .fetchOne(this::mapToAnemia);
    }

    @Override
    public List<IslandMembershipAnemia> performList() {
        return DSL.using(configuration)
                .selectFrom(IslandsMembership.ISLANDS_MEMBERSHIP)
                .where(getWhereConditions())
                .fetch(this::mapToAnemia);
    }

    private List<Condition> getWhereConditions() {
        List<Condition> conditions = new ArrayList<>();
        Optional<IslanderId> islanderIdOpt = request.getIslanderId();
        islanderIdOpt.ifPresent(islanderId -> conditions.add(IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID.eq(islanderId.getInternal())));

        Optional<IslandId> islandIdOpt = request.getIslandId();
        islandIdOpt.ifPresent(islandId -> conditions.add(IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID.eq(islandId.getId())));
        return conditions;
    }

    private IslandMembershipAnemia mapToAnemia(Record record) {
        UUID uuid = record.get(IslandsMembership.ISLANDS_MEMBERSHIP.ISLANDER_ID);
        Integer rawIslandId = record.get(IslandsMembership.ISLANDS_MEMBERSHIP.ISLAND_ID);
        Membershiptype membershiptype = record.get(IslandsMembership.ISLANDS_MEMBERSHIP.MEMBERSHIP_TYPE);
        Islandtype islandtype = record.get(Islands.ISLANDS.TYPE);

        IslandId islandId = IslandId.of(IslandType.valueOf(islandtype.name()), rawIslandId);
        MembershipType membershipType = MembershipType.valueOf(membershiptype.name());
        IslanderId islanderId = IslanderId.of(uuid);
        return new IslandMembershipAnemia(islanderId, islandId, membershipType);
    }

}
