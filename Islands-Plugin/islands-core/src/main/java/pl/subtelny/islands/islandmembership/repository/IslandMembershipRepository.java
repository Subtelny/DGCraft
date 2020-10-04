package pl.subtelny.islands.islandmembership.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.islandmembership.repository.loader.IslandMembership;
import pl.subtelny.islands.islandmembership.repository.loader.IslandMembershipLoader;
import pl.subtelny.islands.islandmembership.repository.remover.IslandMembershipRemoveRequest;
import pl.subtelny.islands.islandmembership.repository.remover.IslandMembershipRemover;
import pl.subtelny.islands.islandmembership.repository.updater.IslandMembershipUpdateRequest;
import pl.subtelny.islands.islandmembership.repository.updater.IslandMembershipUpdater;

import java.util.List;

@Component
public class IslandMembershipRepository {

    private final IslandMembershipLoader loader;

    private final IslandMembershipUpdater updater;

    private final IslandMembershipRemover remover;

    @Autowired
    public IslandMembershipRepository(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        this.loader = new IslandMembershipLoader(databaseConnection);
        this.updater = new IslandMembershipUpdater(databaseConnection, transactionProvider);
        this.remover = new IslandMembershipRemover(databaseConnection, transactionProvider);
    }

    public void createIslandMembership(IslandMember islandMember, IslandId islandId, boolean owner) {
        IslandMembershipUpdateRequest request = IslandMembershipUpdateRequest.request(islandId, islandMember.getId(), owner);
        updater.performAction(request);
    }

    public void removeIslandMembership(IslandMember islandMember, IslandId islandId) {
        IslandMembershipRemoveRequest removeRequest = IslandMembershipRemoveRequest.request(islandId, islandMember.getId());
        remover.performAction(removeRequest);
    }

    public List<IslandMembership> loadIslandMemberships(IslandId islandId) {
        return loader.loadIslandMemberIds(islandId);
    }

}
