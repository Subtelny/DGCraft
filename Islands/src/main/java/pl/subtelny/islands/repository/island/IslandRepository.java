package pl.subtelny.islands.repository.island;

import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.loader.IslandTypeLoadAction;
import pl.subtelny.islands.repository.island.storage.IslandStorage;
import pl.subtelny.islands.repository.island.updater.IslandUpdater;

import java.util.Optional;

@Component
public class IslandRepository {

    private final SkyblockIslandRepository skyblockIslandRepository;

    private final IslandStorage islandStorage;

    private final IslandUpdater islandUpdater;

    private final Configuration configuration;

    @Autowired
    public IslandRepository(SkyblockIslandRepository skyblockIslandRepository,
                            DatabaseConfiguration databaseConfiguration,
                            IslandStorage islandStorage, IslandUpdater islandUpdater) {
        this.skyblockIslandRepository = skyblockIslandRepository;
        this.islandStorage = islandStorage;
        this.configuration = databaseConfiguration.getConfiguration();
        this.islandUpdater = islandUpdater;
    }

    public void updateIsland(Island island) {
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
            Optional<SkyblockIsland> skyblockIslandOpt = skyblockIslandRepository.findSkyblockIsland(islandId);
            if (skyblockIslandOpt.isPresent()) {
                return Optional.of(skyblockIslandOpt.get());
            }
        }
        return Optional.empty();
    }

}
