package pl.subtelny.islands.skyblockisland;

import org.bukkit.plugin.Plugin;
import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandAnemiaLoadAction;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoadRequest;
import pl.subtelny.utilities.log.LogUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOW)
public class SkyblockIslandsInitializer implements DependencyActivator {

    private final SkyblockIslandRepository islandRepository;

    private final DatabaseConnection connection;

    @Autowired
    public SkyblockIslandsInitializer(SkyblockIslandRepository islandRepository, DatabaseConnection connection) {
        this.islandRepository = islandRepository;
        this.connection = connection;
    }

    @Override
    public void activate(Plugin plugin) {
        printStarting();
        long loadedIslands = loadAllSkyblockIslands();
        printSummation(loadedIslands);
    }

    private long loadAllSkyblockIslands() {
        Set<SkyblockIslandId> skyblockIslandIds = loadAllSkyblockIslandIds();
        List<SkyblockIsland> skyblockIslands = loadSkyblockIslands(skyblockIslandIds);
        removeFreeIslandCoordinates(skyblockIslands);
        return skyblockIslands.size();
    }

    private Set<SkyblockIslandId> loadAllSkyblockIslandIds() {
        Configuration configuration = connection.getConfiguration();
        SkyblockIslandLoadRequest request = SkyblockIslandLoadRequest.newBuilder().build();
        SkyblockIslandAnemiaLoadAction action = new SkyblockIslandAnemiaLoadAction(configuration, request);
        return action.performList().stream()
                .map(SkyblockIslandAnemia::getIslandId)
                .collect(Collectors.toSet());
    }

    private List<SkyblockIsland> loadSkyblockIslands(Set<SkyblockIslandId> skyblockIslandIds) {
        return skyblockIslandIds.stream()
                .map(islandRepository::findSkyblockIsland)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private void removeFreeIslandCoordinates(List<SkyblockIsland> skyblockIslands) {
        skyblockIslands.stream().map(SkyblockIsland::getIslandCoordinates)
                .forEach(islandRepository::removeFreeIslandCoordinate);
    }

    private void printStarting() {
        LogUtil.info("========== LOADING SKYBLOCK ISLANDS =========");
        LogUtil.info(" ");
    }

    private void printSummation(long loadedSkyblockIslands) {
        LogUtil.info("  Loaded skyblockIslands: " + loadedSkyblockIslands);
        LogUtil.info(" ");
        LogUtil.info("-============================================");
    }
}
