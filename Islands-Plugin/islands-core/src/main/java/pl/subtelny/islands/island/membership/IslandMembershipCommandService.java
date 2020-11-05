package pl.subtelny.islands.island.membership;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;

@Component
public class IslandMembershipCommandService {

    private final IslandMembershipRepository repository;

    @Autowired
    public IslandMembershipCommandService(IslandMembershipRepository repository) {
        this.repository = repository;
    }

    public void saveIslandMembership(IslandMembership islandMembership) {
        repository.saveIslandMembership(islandMembership);
    }

    public void removeIslandMembership(IslandMembership islandMembership) {
        repository.removeIslandMembership(islandMembership);
    }

}
