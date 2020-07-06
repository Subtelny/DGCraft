package pl.subtelny.islands.repository.island;

import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.repository.island.loader.IslandTypeLoadAction;
import pl.subtelny.islands.repository.island.storage.IslandStorage;
import pl.subtelny.islands.repository.island.updater.IslandUpdater;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;

import java.util.Optional;

@Component
public class IslandRepository {

    private final SkyblockIslandRepository skyblockIslandRepository;

    private final IslandStorage islandStorage;

    private final IslandUpdater islandUpdater;

    private final Configuration configuration;

    @Autowired
    public IslandRepository(SkyblockIslandRepository skyblockIslandRepository,
                            DatabaseConnection databaseConfiguration,
                            IslandStorage islandStorage) {
        this.skyblockIslandRepository = skyblockIslandRepository;
        this.islandStorage = islandStorage;
        Configuration configuration = databaseConfiguration.getConfiguration();
        this.configuration = configuration;
        this.islandUpdater = new IslandUpdater(configuration);
    }

    public void updateIsland(Island island) {
    	islandStorage.updateCache(island.getIslandId(), Optional.of(island));
    	islandUpdater.updateIsland(island);
    }

    public Optional<Island> findIsland(IslandId islandId) {
        return islandStorage.getCache(islandId, islandId1 -> {
            Optional<IslandType> islandTypeOpt = loadIslandType(islandId1);
            if (islandTypeOpt.isPresent()) {
                IslandType islandType = islandTypeOpt.get();
                return loadIslandByIslandType(islandId1, islandType);
            }
            return Optional.empty();
        });
    }

    private Optional<IslandType> loadIslandType(IslandId islandId) {
        return Optional.ofNullable(islandStorage.getIslandTypeCache(islandId, islandId1 -> {
            IslandTypeLoadAction action = new IslandTypeLoadAction(configuration, islandId);
            Optional<IslandType> perform = action.perform();
            return perform.orElse(null);
        }));
    }

    private Optional<Island> loadIslandByIslandType(IslandId islandId, IslandType type) {
        if (type == IslandType.SKYBLOCK) {
            return loadSkyblockIsland(islandId);
        }
        return Optional.empty();
    }

    private Optional<Island> loadSkyblockIsland(IslandId islandId) {
        Optional<SkyblockIsland> skyblockIslandOpt = skyblockIslandRepository.findSkyblockIsland(islandId);
        return skyblockIslandOpt.map(skyblockIsland -> skyblockIsland);
    }

}
