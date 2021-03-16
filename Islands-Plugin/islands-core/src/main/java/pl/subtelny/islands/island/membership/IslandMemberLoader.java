package pl.subtelny.islands.island.membership;

import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandMemberType;

import java.util.List;
import java.util.Optional;

public interface IslandMemberLoader {

    Optional<IslandMember> findIslandMember(IslandMemberId islandMemberId);

    List<IslandMember> getIslandMembers(List<IslandMemberId> islandMemberIds);

    IslandMemberType getType();

}
