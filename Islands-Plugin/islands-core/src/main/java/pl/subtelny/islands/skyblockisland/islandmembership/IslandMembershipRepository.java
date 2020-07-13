package pl.subtelny.islands.skyblockisland.islandmembership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.islandmembership.loader.IslandMembershipLoader;
import pl.subtelny.islands.skyblockisland.islandmembership.model.IslandMembership;
import pl.subtelny.islands.skyblockisland.islandmembership.remover.IslandMembershipRemover;
import pl.subtelny.islands.skyblockisland.islandmembership.storage.IslandMembershipStorage;
import pl.subtelny.islands.skyblockisland.islandmembership.updater.IslandMembershipUpdater;
import pl.subtelny.islands.skyblockisland.model.MembershipType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IslandMembershipRepository {

    private final IslandMembershipLoader loader;

    private final IslandMembershipStorage storage;

    private final IslandMembershipUpdater updater;

    private final IslandMembershipRemover remover;

    @Autowired
    public IslandMembershipRepository(DatabaseConnection databaseConnection) {
        this.loader = new IslandMembershipLoader(databaseConnection);
        this.updater = new IslandMembershipUpdater(databaseConnection);
        this.remover = new IslandMembershipRemover(databaseConnection);
        this.storage = new IslandMembershipStorage();
    }

    public void updateIslandMembership(Island island) {
        Map<Islander, MembershipType> actualMembership = island.getMembers();
        List<IslanderId> storedIslandersForIsland = storage.getIslandMemberIds(island.getIslandId());

        removeNotNeededIslanders(storedIslandersForIsland, toIslanderIds(actualMembership));
        saveIslandMembers(island, actualMembership);
    }

    public List<IslandMembership> findIslandMembership(IslandId islandId) {
        return loader.loadMembership(islandId);
    }

    private List<IslanderId> toIslanderIds(Map<Islander, MembershipType> actualMembership) {
        return actualMembership.keySet().stream().map(Islander::getIslanderId).collect(Collectors.toList());
    }

    private void removeNotNeededIslanders(List<IslanderId> storedIslandersForIsland, List<IslanderId> actualIslanders) {
        List<IslanderId> toRemove = storedIslandersForIsland.stream()
                .filter(islanderId -> !actualIslanders.contains(islanderId))
                .collect(Collectors.toList());
        remover.removeAsync(toRemove);
        toRemove.forEach(storage::invalidate);
    }

    private void saveIslandMembers(Island island, Map<Islander, MembershipType> actualMembership) {
        actualMembership.entrySet().stream()
                .map(entry -> new IslandMembership(entry.getKey().getIslanderId(), island.getIslandId(), entry.getValue()))
                .forEach(updater::updateAsync);
    }

}
