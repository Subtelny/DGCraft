package pl.subtelny.islands.skyblockisland.islandmembership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.islandmembership.loader.IslandMembershipLoader;
import pl.subtelny.islands.skyblockisland.islandmembership.remover.IslandMembershipRemover;
import pl.subtelny.islands.skyblockisland.islandmembership.updater.IslandMembershipUpdater;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
import java.util.Map;
import java.util.Set;

@Component
public class IslandMembershipRepository {

    private final IslandMembershipLoader loader;

    private final IslandMembershipUpdater updater;

    private final IslandMembershipRemover remover;

    @Autowired
    public IslandMembershipRepository(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        this.loader = new IslandMembershipLoader(databaseConnection);
        this.updater = new IslandMembershipUpdater(databaseConnection, transactionProvider);
        this.remover = new IslandMembershipRemover(databaseConnection);
    }

    public void addIslandMembership(IslandId islandId, IslanderId islanderId, MembershipType membershipType) {
        updater.update(islandId, islanderId, membershipType);
    }

    public void removeIslandMembership(IslandId islandId, IslanderId islanderId) {
        remover.remove(islandId, islanderId);
    }

    public void updateIslandMembership(IslandId islandId, Map<IslanderId, MembershipType> islanderIds) {
        islanderIds.forEach((islanderId, membershipType) -> addIslandMembership(islandId, islanderId, membershipType));
        removeNotInIslandMembers(islandId, islanderIds.keySet());
    }

    public Map<IslanderId, MembershipType> findIslandMemberships(IslandId islandId) {
        return loader.loadMembership(islandId);
    }

    private void removeNotInIslandMembers(IslandId islandId, Set<IslanderId> islanderIds) {
        remover.removeNotIn(islandId, islanderIds.toArray(new IslanderId[0]));
    }

}
