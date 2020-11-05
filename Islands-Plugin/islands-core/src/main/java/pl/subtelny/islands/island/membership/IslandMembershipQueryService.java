package pl.subtelny.islands.island.membership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;

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

    public List<IslandMembership> loadIslandMemberships(IslandId islandId) {
        return islandMembershipRepository.loadIslandMemberships(islandId);
    }

}
