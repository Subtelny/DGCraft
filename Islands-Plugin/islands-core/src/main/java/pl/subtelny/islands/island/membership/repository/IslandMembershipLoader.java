package pl.subtelny.islands.island.membership.repository;

import org.jooq.DSLContext;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.islandmembership.repository.loader.IslandMembership;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IslandMembershipLoader {

    private final ConnectionProvider connectionProvider;

    @Autowired
    public IslandMembershipLoader(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<IslandMembership> loadIslandMemberships(IslandMemberId islandMemberId) {
        IslandMembershipLoadRequest request = IslandMembershipLoadRequest.newBuilder()
                .where(islandMemberId)
                .build();
        return performAction(request);
    }

    public List<IslandMembership> loadIslandMemberships(IslandId islandId) {
        IslandMembershipLoadRequest request = IslandMembershipLoadRequest.newBuilder()
                .where(islandId)
                .build();
        return performAction(request);
    }

    private List<IslandMembership> performAction(IslandMembershipLoadRequest request) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        IslandMembershipLoadAction action = new IslandMembershipLoadAction(currentConnection, request);
        return action.performList().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private IslandMembership toDomain(IslandMembershipAnemia anemia) {
        return new IslandMembership(anemia.getIslandId(), anemia.getIslandMemberId(), anemia.isOwner());
    }

}
