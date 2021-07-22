package pl.subtelny.islands.api.cqrs.command;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.cqrs.IslandService;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.api.module.component.CreateComponent;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.log.LogUtil;

@Component
public class IslandCreateService extends IslandService {

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandCreateService(IslandModules islandModules,
                               IslanderQueryService islanderQueryService) {
        super(islandModules);
        this.islanderQueryService = islanderQueryService;
    }

    public void createIsland(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        CreateComponent<IslandMember, Island> createComponent = getCreateComponent(islandType);
        createComponent.create(islander)
                .whenComplete((island, throwable) -> {
                    if (throwable != null) {
                        islandFailureCreate(player, throwable);
                    } else {
                        islandSucessfullyCreated(player, island);
                    }
                });
    }

    private CreateComponent<IslandMember, Island> getCreateComponent(IslandType islandType) {
        IslandModule<Island> islandModule = getIslandModule(islandType);
        return islandModule.getComponent(CreateComponent.class);
    }

    private void islandFailureCreate(Player player, Throwable throwable) {
        LogUtil.warning("Error while creating island: " + throwable.getMessage());
        throwable.printStackTrace();
        IslandMessages.get().sendTo(player, "islandCreate.internal_error");
    }

    private void islandSucessfullyCreated(Player player, Island island) {
        JobsProvider.runSync(Islands.PLUGIN, () -> {
            player.teleport(island.getSpawn());
            IslandMessages.get().sendTo(player, "islandCreate.island_created");
        });
    }

}
