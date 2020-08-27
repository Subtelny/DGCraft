package pl.subtelny.islands.islandmembership.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.islandmembership.dto.IslandMembershipDTO;
import pl.subtelny.repository.Storage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IslandMembershipStorage extends Storage<IslandMemberId, IslandMembershipDTO> {

    public IslandMembershipStorage() {
        super(Caffeine.newBuilder().build());
    }

    public List<IslandMemberId> getIslandMemberIds(IslandId islandId) {
        return getAllCache().entrySet().stream()
                .filter(entry -> entry.getValue().getIslandId().equals(islandId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
