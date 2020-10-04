package pl.subtelny.islands.island.repository;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.model.IslandMemberChangedRequest;
import pl.subtelny.islands.island.repository.loader.IslandTypeLoader;
import pl.subtelny.islands.islandmembership.repository.IslandMembershipRepository;
import pl.subtelny.utilities.NullObject;
import java.util.List;
import java.util.Optional;

@Component
public class IslandRepository {

    private final TransactionProvider transactionProvider;

    private final IslandTypeLoader islandTypeLoader;

    private final IslandStorage islandStorage;

    private final List<IslandRepositoryConnector<?>> connectors;

    private final IslandMembershipRepository islandMembershipRepository;

    @Autowired
    public IslandRepository(TransactionProvider transactionProvider, DatabaseConnection databaseConnection, List<IslandRepositoryConnector<?>> connectors, IslandMembershipRepository islandMembershipRepository) {
        this.transactionProvider = transactionProvider;
        this.islandTypeLoader = new IslandTypeLoader(databaseConnection);
        this.connectors = connectors;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islandStorage = new IslandStorage();
    }

    public IslandId updateIsland(Island island) {
        IslandRepositoryConnector connector = getConnector(island.getType());
        SaveIslandJob job = new SaveIslandJob(island, connector);
        return job.save();
    }

    public Optional<Island> findIsland(IslandId islandId) {
        return islandStorage.getCache(islandId, this::loadIsland).get();
    }

    private NullObject<Island> loadIsland(IslandId islandId) {
        Optional<IslandType> islandTypeOpt = islandTypeLoader.findIslandType(islandId);
        return islandTypeOpt.map(islandType -> {
            Optional<Island> island = getConnector(islandType).loadIsland(islandId);
            return island.map(NullObject::of).orElse(NullObject.empty());
        }).orElse(NullObject.empty());
    }

    private IslandRepositoryConnector getConnector(IslandType islandType) {
        return connectors.stream()
                .filter(islandRepositoryConnector -> islandRepositoryConnector.getType().equals(islandType))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Not found connector for islandType " + islandType));
    }

    private class SaveIslandJob {

        private final Island island;

        private final IslandRepositoryConnector islandRepositoryConnector;

        private SaveIslandJob(Island island, IslandRepositoryConnector islandRepositoryConnector) {
            this.island = island;
            this.islandRepositoryConnector = islandRepositoryConnector;
        }

        public IslandId save() {
           return transactionProvider.transactionResult(() -> {
                Island island = islandRepositoryConnector.updateIsland(this.island);
                saveIslandMembers(island);
                islandStorage.put(island.getId(), NullObject.of(island));
                return island.getId();
            });
        }

        private void saveIslandMembers(Island island) {
            AbstractIsland abstractIsland = (AbstractIsland) island;
            List<IslandMemberChangedRequest> requests = abstractIsland.getIslandMembersChangesRequests();
            requests.forEach(request -> handleIslandMemberChangedRequest(island, request));
        }

        private <T extends Island> void handleIslandMemberChangedRequest(T island, IslandMemberChangedRequest request) {
            switch (request.getType()) {
                case ADDED:
                    islandMembershipRepository.createIslandMembership(request.getIslandMember(), island.getId(), request.isOwner());
                    break;
                case REMOVED:
                    islandMembershipRepository.removeIslandMembership(request.getIslandMember(), island.getId());
                    break;
            }
        }

    }

}
