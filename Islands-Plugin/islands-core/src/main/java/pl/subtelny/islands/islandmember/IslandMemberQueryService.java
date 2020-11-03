package pl.subtelny.islands.islandmember;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class IslandMemberQueryService {

    public Optional<IslandMember> findIslandMember(IslandMemberId islandMemberId) {
        return Optional.empty();
    }

    public List<IslandMember> getIslandMembers(List<IslandMemberId> islandMemberIds) {

        return Arrays.asList();
    }

}
