package pl.subtelny.islands.api.listeners;

import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;

import java.util.List;
import java.util.stream.Collectors;

public abstract class IslandMemberIslandEventListener {

    private final IslandMemberQueryService islandMemberQueryService;

    protected IslandMemberIslandEventListener(IslandMemberQueryService islandMemberQueryService) {
        this.islandMemberQueryService = islandMemberQueryService;
    }

    protected List<IslandMember> getAllExceptIslandMember(List<IslandMemberId> islandMemberIds, IslandMember islandMember) {
        IslandMemberId islandMemberId1 = islandMember.getIslandMemberId();
        List<IslandMemberId> exceptIslandMember = islandMemberIds.stream()
                .filter(islandMemberId -> !islandMemberId.equals(islandMemberId1))
                .collect(Collectors.toList());
        return islandMemberQueryService.getIslandMembers(exceptIslandMember);
    }

}
