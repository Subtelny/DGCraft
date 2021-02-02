package pl.subtelny.islands.island.membership.repository;

import org.jooq.DSLContext;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.membership.repository.load.IslandMembershipLoadAction;
import pl.subtelny.islands.island.membership.repository.load.IslandMembershipLoadRequest;
import pl.subtelny.islands.island.membership.repository.remove.IslandMembershipRemoveAction;
import pl.subtelny.islands.island.membership.repository.remove.IslandMembershipRemoveRequest;
import pl.subtelny.islands.island.membership.repository.update.IslandMembershipUpdateAction;
import pl.subtelny.islands.island.membership.repository.update.IslandMembershipUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IslandMembershipRepository {

    private final ConnectionProvider connectionProvider;

    @Autowired
    public IslandMembershipRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void saveIslandMembership(IslandMembership islandMembership) {
        IslandMembershipUpdateRequest request = IslandMembershipUpdateRequest.request(islandMembership.getIslandId(),
                islandMembership.getIslandMemberId(), islandMembership.isOwner());
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        IslandMembershipUpdateAction action = new IslandMembershipUpdateAction(currentConnection);
        action.perform(request);
    }

    public void removeIslandMemberships(IslandId islandId) {
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        IslandMembershipRemoveAction action = new IslandMembershipRemoveAction(currentConnection);
        IslandMembershipRemoveRequest request = IslandMembershipRemoveRequest.request(islandId);
        action.perform(request);
    }

    public void removeIslandMembership(IslandMembership islandMembership) {
        IslandMembershipRemoveRequest request = IslandMembershipRemoveRequest.request(
                islandMembership.getIslandId(), islandMembership.getIslandMemberId());
        DSLContext currentConnection = connectionProvider.getCurrentConnection();
        IslandMembershipRemoveAction action = new IslandMembershipRemoveAction(currentConnection);
        action.perform(request);
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
        return new IslandMembership(anemia.getIslandMemberId(), anemia.getIslandId(), anemia.isOwner());
    }

}
