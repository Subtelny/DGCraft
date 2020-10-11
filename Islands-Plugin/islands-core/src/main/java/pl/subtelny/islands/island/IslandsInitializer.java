package pl.subtelny.islands.island;

import org.bukkit.plugin.Plugin;
import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.islands.island.repository.IslandRepository;
import pl.subtelny.utilities.log.LogUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOW)
public class IslandsInitializer implements DependencyActivator {

    private final IslandRepository islandRepository;

    private final DatabaseConnection connection;

    @Autowired
    public IslandsInitializer(IslandRepository islandRepository, DatabaseConnection connection) {
        this.islandRepository = islandRepository;
        this.connection = connection;
    }

    @Override
    public void activate(Plugin plugin) {
        printStarting();
        loadAllIslands();
        printEnding();
    }

    private void loadAllIslands() {
        List<IslandId> islandIds = getIslandIds();
        List<Island> islands = islandIds.stream()
                .map(islandRepository::findIsland)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        printFoundedIslandsInDatabase(islandIds.size());
        printSummation(islands.stream().collect(Collectors.groupingBy(Island::getType)));
    }

    private List<IslandId> getIslandIds() {
        return DSL.using(connection.getConfiguration())
                .select(Islands.ISLANDS.ID)
                .from(Islands.ISLANDS)
                .fetch(record -> IslandId.of(record.get(Islands.ISLANDS.ID)));
    }


    private void printStarting() {
        LogUtil.info("========== LADOWANIE ISLANDS =========");
        LogUtil.info(" ");
    }

    private void printFoundedIslandsInDatabase(int foundedIslands) {
        LogUtil.info(" W bazie jest wysp: " + foundedIslands);
    }

    private void printSummation(Map<IslandType, List<Island>> islandsSummation) {
        LogUtil.info(" Zaladowanych wysp:");
        islandsSummation.forEach((islandType, amountIslands) -> LogUtil.info(" " + islandType.getInternal() + ": " + amountIslands.size()));
    }

    private void printEnding() {
        LogUtil.info(" ");
        LogUtil.info("=====================================");
    }
}
