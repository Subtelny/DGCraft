package pl.subtelny.islands.island.cqrs.command;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.IslandService;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.log.LogUtil;

import java.util.concurrent.CompletableFuture;

@Component
public class IslandCreateService extends IslandService {

    private final TransactionProvider transactionProvider;

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandCreateService(IslandModules islandModules,
                               TransactionProvider transactionProvider,
                               IslanderQueryService islanderQueryService) {
        super(islandModules);
        this.transactionProvider = transactionProvider;
        this.islanderQueryService = islanderQueryService;
    }

    public void createIsland(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        IslandCreateRequest request = IslandCreateRequest.newBuilder(islandType)
                .setOwner(islander)
                .build();

        createIsland(request)
                .whenComplete((island, throwable) -> {
                    if (throwable != null) {
                        islandFailureCreate(player, throwable);
                    } else {
                        islandSucessfullyCreated(player, island);
                    }
                });
    }

    private CompletableFuture<Island> createIsland(IslandCreateRequest request) {
        validateIslandCreate(request);
        IslandModule<Island> islandModule = getIslandModule(request.getIslandType());
        return transactionProvider
                .transactionResultAsync(() -> islandModule.createIsland(request)).toCompletableFuture()
                .thenApply(island -> {
                    request.getOwner().ifPresent(member -> member.addIsland(island));
                    return island;
                });
    }

    private void validateIslandCreate(IslandCreateRequest request) {
        Boolean hasIsland = request.getOwner().
                map(islandMember -> islandMember.hasIsland(request.getIslandType()))
                .orElse(false);
        Validation.isFalse(hasIsland, "islandCreate.already_has_island", request.getIslandType());
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
