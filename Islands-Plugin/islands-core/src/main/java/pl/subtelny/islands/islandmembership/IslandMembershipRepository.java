package pl.subtelny.islands.islandmembership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.islandmembership.updater.IslandMembershipUpdater;
import pl.subtelny.islands.islandmembership.loader.IslandMembershipLoader;
import pl.subtelny.islands.islandmembership.remover.IslandMembershipRemover;
import java.util.Map;

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

    public void updateIslandMembership(Island island) {
        Map<IslandMember, IslandMemberRank> members = island.getMembers();
        removeNotInIslandMembers(island);
    }

    private void removeNotInIslandMembers(Island island) {
        remover.removeNotIn(island.getId(), island.getMembers().keySet());
    }
}
