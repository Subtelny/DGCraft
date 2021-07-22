package pl.subtelny.islands.api.cqrs.command;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.cqrs.IslandService;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.api.module.component.DeleteComponent;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;

@Component
public class IslandDeleteService extends IslandService {

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandDeleteService(IslandModules islandModules,
                               IslanderQueryService islanderQueryService) {
        super(islandModules);
        this.islanderQueryService = islanderQueryService;
    }

    public void deleteIsland(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        Island island = getIsland(islander, islandType);

        DeleteComponent deleteComponent = getDeleteComponent(island.getId());
        deleteComponent.delete(islander, island.getId());
    }

    private DeleteComponent getDeleteComponent(IslandId islandId) {
        IslandModule<Island> islandModule = getIslandModule(islandId.getIslandType());
        return islandModule.getComponent(DeleteComponent.class);
    }

}
