package pl.subtelny.islands.api.membership;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.cqrs.IslandService;
import pl.subtelny.islands.api.membership.model.IslandMembership;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;

@Component
public class IslandMembershipService extends IslandService {

    private final IslanderQueryService islanderQueryService;

    private final IslandMembershipRepository repository;

    @Autowired
    public IslandMembershipService(IslandModules islandModules,
                                   IslanderQueryService islanderQueryService,
                                   IslandMembershipRepository repository) {
        super(islandModules);
        this.islanderQueryService = islanderQueryService;
        this.repository = repository;
    }

    public void join(IslandId islandId, IslandMember islandMember) {
        Island island = getIsland(islandId);
        join(island, islandMember);
    }

    public void leave(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        Island island = getIsland(islander, islandType);
        leave(island, islander);
    }

    private void join(Island island, IslandMember islandMember) {
        island.join(islandMember);
        IslandMembership member = IslandMembership.member(islandMember.getIslandMemberId(), island.getId());
        repository.saveIslandMembership(member);
    }

    private void leave(Island island, IslandMember islandMember) {
        island.leave(islandMember);
        IslandMembership member = IslandMembership.member(islandMember.getIslandMemberId(), island.getId());
        repository.removeIslandMembership(member);
    }

}
