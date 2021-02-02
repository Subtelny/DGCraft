package pl.subtelny.islands.island.membership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandMemberType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IslandMemberQueryService {

    private final List<IslandMemberLoader> islandMemberLoaders;

    @Autowired
    public IslandMemberQueryService(List<IslandMemberLoader> islandMemberLoaders) {
        this.islandMemberLoaders = islandMemberLoaders;
    }

    public Optional<IslandMember> findIslandMember(IslandMemberId islandMemberId) {
        IslandMemberType type = islandMemberId.getType();
        IslandMemberLoader islandMemberLoader = getIslandMemberLoader(type);
        return islandMemberLoader.findIslandMember(islandMemberId);
    }

    public List<IslandMember> getIslandMembers(List<IslandMemberId> islandMemberIds) {
        return islandMemberIds.stream()
                .map(this::findIslandMember)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private IslandMemberLoader getIslandMemberLoader(IslandMemberType type) {
        return islandMemberLoaders.stream()
                .filter(islandMemberLoader -> islandMemberLoader.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found islandMemberLoader by type" + type.getInternal()));
    }

}
