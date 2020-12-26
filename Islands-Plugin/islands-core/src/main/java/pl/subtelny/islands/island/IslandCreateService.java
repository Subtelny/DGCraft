package pl.subtelny.islands.island;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.log.LogUtil;

@Component
public class IslandCreateService {

    private final IslanderQueryService islanderQueryService;

    private final IslandCommandService islandCommandService;

    private final IslandMessages messages;

    @Autowired
    public IslandCreateService(IslanderQueryService islanderQueryService,
                               IslandCommandService islandCommandService,
                               IslandMessages messages) {
        this.islanderQueryService = islanderQueryService;
        this.islandCommandService = islandCommandService;
        this.messages = messages;
    }

    public void createIsland(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        IslandCreateRequest request = IslandCreateRequest.newBuilder(islandType)
                .setOwner(islander)
                .build();

        islandCommandService.createIsland(request)
                .whenComplete((island, throwable) -> {
                    if (throwable != null) {
                        islandFailureCreate(player, throwable);
                    } else {
                        islandSucessfullyCreated(player, island);
                    }
                });
    }

    private void islandFailureCreate(Player player, Throwable throwable) {
        LogUtil.warning("Error while creating island: " + throwable.getMessage());
        throwable.printStackTrace();
        messages.sendTo(player, "islandCreate.internal_error");
    }

    private void islandSucessfullyCreated(Player player, Island island) {
        JobsProvider.runSync(Islands.plugin, () -> {
            player.teleport(island.getSpawn());
            messages.sendTo(player, "islandCreate.island_created");
        });
    }

}
