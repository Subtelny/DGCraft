package pl.subtelny.islands.skyblockisland.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.islandold.repository.IslandRepositoryConnector;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.islandmembership.repository.IslandMembershipRepository;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidCalculator;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.loader.SkyblockIslandLoader;
import pl.subtelny.islands.islandold.repository.remover.IslandRemover;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandCache;
import pl.subtelny.islands.skyblockisland.repository.updater.SkyblockIslandUpdater;

import java.util.Optional;

@Component
public class SkyblockIslandRepositoryConnector implements IslandRepositoryConnector<SkyblockIsland> {

    private final SkyblockIslandCache skyblockIslandCache;

    private final SkyblockIslandLoader loader;

    private final SkyblockIslandUpdater updater;

    private final IslandRemover remover;

    @Autowired
    public SkyblockIslandRepositoryConnector(SkyblockIslandCache skyblockIslandCache,
                                             DatabaseConnection databaseConfiguration,
                                             SkyblockIslandExtendCuboidCalculator extendCuboidCalculator,
                                             TransactionProvider transactionProvider,
                                             IslandMembershipRepository islandMembershipRepository,
                                             IslanderRepository islanderRepository,
                                             IslandRemover remover) {
        this.skyblockIslandCache = skyblockIslandCache;
        this.remover = remover;
        this.loader = new SkyblockIslandLoader(databaseConfiguration, extendCuboidCalculator, transactionProvider, islandMembershipRepository, islanderRepository);
        this.updater = new SkyblockIslandUpdater(databaseConfiguration, transactionProvider);
    }

    @Override
    public Optional<SkyblockIsland> loadIsland(IslandId islandId) {
        Optional<SkyblockIsland> skyblockIslandOpt = loader.loadIsland(islandId);
        skyblockIslandOpt.ifPresentOrElse(skyblockIslandCache::updateIslandCache, () -> skyblockIslandCache.invalidateIsland(islandId));
        return skyblockIslandOpt;
    }

    @Override
    public SkyblockIsland updateIsland(SkyblockIsland island) {
        IslandId islandId = updater.update(island);
        return loadIsland(islandId)
                .orElseThrow(() -> new IllegalStateException("Could not find island after update island " + islandId));
    }

    @Override
    public void removeIsland(SkyblockIsland island) {
        remover.removeIsland(island);
    }

    @Override
    public IslandType getType() {
        return SkyblockIsland.TYPE;
    }

}
