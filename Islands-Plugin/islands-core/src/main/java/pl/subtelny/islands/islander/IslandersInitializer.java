package pl.subtelny.islands.islander;

import org.bukkit.plugin.Plugin;
import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.repository.loader.IslanderAnemiaLoadAction;
import pl.subtelny.islands.islander.repository.loader.IslanderLoadRequest;
import pl.subtelny.utilities.log.LogUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOW)
public class IslandersInitializer implements DependencyActivator {

    private final IslanderRepository islanderRepository;

    private final Configuration configuration;

    @Autowired
    public IslandersInitializer(DatabaseConnection databaseConnection, IslanderRepository islanderRepository) {
        this.islanderRepository = islanderRepository;
        this.configuration = databaseConnection.getConfiguration();
    }

    @Override
    public void activate(Plugin plugin) {
        System.out.println("heheh");
        initializeAllIslanders();
    }

    private void initializeAllIslanders() {
        printStarting();
        long loadedIslanders = loadIslanders();
        printSummation(loadedIslanders);
    }

    private long loadIslanders() {
        List<IslanderId> islanderIds = loadIslanderIds();
        return islanderIds.stream()
                .map(islanderRepository::findIslander)
                .filter(Optional::isPresent)
                .count();
    }

    private List<IslanderId> loadIslanderIds() {
        IslanderLoadRequest request = IslanderLoadRequest.newBuilder().build();
        IslanderAnemiaLoadAction action = new IslanderAnemiaLoadAction(configuration, request);
        return action.performList().stream().map(IslanderAnemia::getIslanderId).collect(Collectors.toList());
    }

    private void printStarting() {
        LogUtil.info("============ LOADING ISLANDERS ============");
    }

    private void printSummation(long loaderIslanders) {
        LogUtil.info("  Loaded islanders: " + loaderIslanders);
        LogUtil.info("===========================================");
    }
}
