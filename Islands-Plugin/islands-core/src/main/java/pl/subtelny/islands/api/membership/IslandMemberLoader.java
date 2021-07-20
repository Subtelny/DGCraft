package pl.subtelny.islands.api.membership;

import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.IslandMemberType;

import java.util.List;
import java.util.Optional;

public interface IslandMemberLoader {

    Optional<IslandMember> findIslandMember(IslandMemberId islandMemberId);

    List<IslandMember> getIslandMembers(List<IslandMemberId> islandMemberIds);

    IslandMemberType getType();

}
