package pl.subtelny.islands.api.cqrs.command;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.api.module.component.InviteComponent;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;

@Component
public class IslandInviteService {

    private static final IslandType ACTUAL_SEASON_ISLAND_TYPE = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;

    private final IslandModules islandModules;

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandInviteService(IslandModules islandModules,
                               IslanderQueryService islanderQueryService) {
        this.islandModules = islandModules;
        this.islanderQueryService = islanderQueryService;
    }

    public void invite(Player inviter, Player target) {
        Islander targetIslander = islanderQueryService.getIslander(target);
        Islander inviterIslander = islanderQueryService.getIslander(inviter);
        getInviteComponent().invite(inviterIslander, targetIslander);
    }

    public void ask(Player asker, Player target) {
        Islander askerIslander = islanderQueryService.getIslander(asker);
        Islander targetIslander = islanderQueryService.getIslander(target);
        getInviteComponent().ask(askerIslander, targetIslander);
    }

    public void ask(Player asker, IslandId islandId) {
        Islander askerIslander = islanderQueryService.getIslander(asker);
        Island targetIsland = getIslandModule().findIsland(islandId).orElseThrow(() -> new IllegalStateException(""));
        getInviteComponent().ask(askerIslander, targetIsland);
    }

    private InviteComponent<IslandMember, IslandMember, Island> getInviteComponent() {
        IslandModule<Island> islandModule = getIslandModule();
        return islandModule.getComponent(InviteComponent.class);
    }

    private IslandModule<Island> getIslandModule() {
        return islandModules.findIslandModule(ACTUAL_SEASON_ISLAND_TYPE)
                .orElseThrow(() -> new IllegalStateException("IslandModule not found " + ACTUAL_SEASON_ISLAND_TYPE.getInternal()));
    }

}
