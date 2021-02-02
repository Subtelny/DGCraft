package pl.subtelny.islands.island.membership;

import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandMemberType;

import java.util.List;
import java.util.Optional;

public interface IslandMemberLoader<T extends IslandMember> {

    Optional<T> findIslandMember(IslandMemberId islandMemberId);

    List<T> getIslandMembers(List<IslandMemberId> islandMemberIds);

    IslandMemberType getType();

}
