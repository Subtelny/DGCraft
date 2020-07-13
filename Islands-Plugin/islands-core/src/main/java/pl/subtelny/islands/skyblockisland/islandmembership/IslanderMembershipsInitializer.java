package pl.subtelny.islands.skyblockisland.islandmembership;

import org.bukkit.plugin.Plugin;
import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.islands.skyblockisland.islandmembership.loader.IslandMembershipAnemiaLoadAction;
import pl.subtelny.islands.skyblockisland.islandmembership.loader.IslandMembershipLoadRequest;
import pl.subtelny.utilities.log.LogUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOWEST)
public class IslanderMembershipsInitializer implements DependencyActivator {

    private final DatabaseConnection databaseConnection;

    private final IslandMembershipRepository repository;

    @Autowired
    public IslanderMembershipsInitializer(DatabaseConnection databaseConnection, IslandMembershipRepository repository) {
        this.databaseConnection = databaseConnection;
        this.repository = repository;
    }

    @Override
    public void activate(Plugin plugin) {
        printStarting();
        long loadedIslandMemberships = loadIslandMemberships();
        printSummation(loadedIslandMemberships);
    }

    private long loadIslandMemberships() {
        Set<IslandId> islandIds = loadIslandIds();
        return islandIds.stream()
                .mapToLong(islandId -> repository.findIslandMembership(islandId).size())
                .sum();
    }

    private Set<IslandId> loadIslandIds() {
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipLoadRequest request = IslandMembershipLoadRequest.newBuilder().build();
        IslandMembershipAnemiaLoadAction anemiaLoadAction = new IslandMembershipAnemiaLoadAction(configuration, request);
        List<IslandMembershipAnemia> islandMembershipAnemias = anemiaLoadAction.performList();
        return islandMembershipAnemias.stream()
                .map(IslandMembershipAnemia::getIslandId)
                .collect(Collectors.toSet());
    }

    private void printStarting() {
        LogUtil.info("========== LOADING ISLAND MEMBERSHIPS =========");
        LogUtil.info(" ");
    }

    private void printSummation(long value) {
        LogUtil.info("  Loaded islandMemberships: " + value);
        LogUtil.info(" ");
        LogUtil.info("-============================================");
    }

}
