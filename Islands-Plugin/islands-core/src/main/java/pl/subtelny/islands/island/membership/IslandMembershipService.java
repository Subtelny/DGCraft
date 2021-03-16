package pl.subtelny.islands.island.membership;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.IslandService;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class IslandMembershipService extends IslandService {

    private final IslanderQueryService islanderQueryService;

    private final IslandMembershipRepository repository;

    protected IslandMembershipService(IslandModules islandModules,
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

    public void join(Island island, IslandMember islandMember) {
        island.join(islandMember);
        IslandMembership member = IslandMembership.member(islandMember.getIslandMemberId(), island.getId());
        repository.saveIslandMembership(member);
    }

    public void leave(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        IslandId islandId = islander.getIsland(islandType).orElseThrow(() -> ValidationException.of("islandMembership.island_not_found"));
        leave(islandId, islander);
    }

    public void leave(IslandId islandId, IslandMember islandMember) {
        Island island = getIsland(islandId);
        leave(island, islandMember);
    }

    public void leave(Island island, IslandMember islandMember) {
        island.leave(islandMember);
        IslandMembership member = IslandMembership.member(islandMember.getIslandMemberId(), island.getId());
        repository.removeIslandMembership(member);
    }

}
