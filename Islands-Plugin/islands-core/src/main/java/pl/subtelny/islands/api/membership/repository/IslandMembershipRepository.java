package pl.subtelny.islands.api.membership.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.repository.HeavyRepository;
import pl.subtelny.generated.tables.tables.IslandMemberships;
import pl.subtelny.generated.tables.tables.records.IslandMembershipsRecord;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.membership.model.IslandMembership;

import java.util.List;

@Component
public class IslandMembershipRepository extends HeavyRepository {

    @Autowired
    public IslandMembershipRepository(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    public void saveIslandMembership(IslandMembership islandMembership) {
        session(context -> {
            IslandMembershipAnemia.toAnemia(islandMembership).toRecord(context).store();
        });
    }

    public void removeIslandMemberships(IslandId islandId) {
        session(context -> {
            context.deleteFrom(IslandMemberships.ISLAND_MEMBERSHIPS)
                    .where(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID.eq(islandId.getInternal()))
                    .execute();
        });
    }

    public void removeIslandMembership(IslandMembership islandMembership) {
        IslandId islandId = islandMembership.getIslandId();
        IslandMemberId islandMemberId = islandMembership.getIslandMemberId();
        session(context -> {
            context.deleteFrom(IslandMemberships.ISLAND_MEMBERSHIPS)
                    .where(
                            IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID.eq(islandId.getInternal())
                                    .and(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID.eq(islandMemberId.getInternal()))
                    ).execute();
        });
    }

    public List<IslandMembership> loadIslandMemberships(IslandMemberId islandMemberId) {
        return session(context -> {
            return context.selectFrom(IslandMemberships.ISLAND_MEMBERSHIPS)
                    .where(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_MEMBER_ID.eq(islandMemberId.getInternal()))
                    .fetch(this::mapToDomain);
        });
    }

    public List<IslandMembership> loadIslandMemberships(IslandId islandId) {
        return session(context -> {
            return context.selectFrom(IslandMemberships.ISLAND_MEMBERSHIPS)
                    .where(IslandMemberships.ISLAND_MEMBERSHIPS.ISLAND_ID.eq(islandId.getInternal()))
                    .fetch(this::mapToDomain);
        });
    }

    private IslandMembership mapToDomain(IslandMembershipsRecord record) {
        return IslandMembershipAnemia.toAnemia(record).toDomain();
    }

}
