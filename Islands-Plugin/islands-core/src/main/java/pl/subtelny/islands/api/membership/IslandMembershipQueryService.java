package pl.subtelny.islands.api.membership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.membership.model.IslandMembership;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;

import java.util.List;

@Component
public class IslandMembershipQueryService {

    private final IslandMembershipRepository islandMembershipRepository;

    @Autowired
    public IslandMembershipQueryService(IslandMembershipRepository islandMembershipRepository) {
        this.islandMembershipRepository = islandMembershipRepository;
    }

    public List<IslandMembership> loadIslandMemberships(IslandMemberId islandMemberId) {
        return islandMembershipRepository.loadIslandMemberships(islandMemberId);
    }

}
