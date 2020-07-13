package pl.subtelny.islands.skyblockisland.islandmembership.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.islandmembership.model.IslandMembership;
import pl.subtelny.repository.Storage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IslandMembershipStorage extends Storage<IslanderId, IslandMembership> {

    public IslandMembershipStorage() {
        super(Caffeine.newBuilder().build());
    }

    public List<IslanderId> getIslandMemberIds(IslandId islandId) {
        return getAllCache().entrySet().stream()
                .filter(entry -> entry.getValue().getIslandId().equals(islandId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
