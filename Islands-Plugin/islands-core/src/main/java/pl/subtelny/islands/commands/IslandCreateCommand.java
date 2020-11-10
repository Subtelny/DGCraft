package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandCreateRequest;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.log.LogUtil;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslanderQueryService islanderService;

    private final IslandModules islandModules;

    @Autowired
    public IslandCreateCommand(IslandMessages messages,
                               IslanderQueryService islanderService,
                               IslandModules islandModules) {
        super(messages);
        this.islanderService = islanderService;
        this.islandModules = islandModules;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);

        IslandType actualSeasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        if (islander.hasIsland(actualSeasonIslandType)) {
            throw ValidationException.of("command.island.create.out_of_island_type_size");
        }

        IslandModule<Island> islandModule = getIslandModule(actualSeasonIslandType);
        IslandCreateRequest request = IslandCreateRequest.newBuilder()
                .setOwner(islander)
                .build();
        createIsland(player, islandModule, request);
    }

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return islandModules.findIslandModule(islandType)
                .orElseThrow(() -> ValidationException.of("command.island.create.island_module_not_found"));
    }

    private void createIsland(Player player, IslandModule islandModule, IslandCreateRequest request) {
        try {
            Island island = islandModule.createIsland(request);
            islandSucessfullyCreated(player, island);
        } catch (IllegalStateException e) {
            islandFailureCreate(player, e);
        }
    }

    private void islandFailureCreate(Player player, Throwable throwable) {
        LogUtil.warning("Error while creating island: " + throwable.getMessage());
        throwable.printStackTrace();
        getMessages().sendTo(player, "command.island.create.internal_error");
    }

    private void islandSucessfullyCreated(Player player, Island island) {
        JobsProvider.runSync(Islands.plugin, () -> {
            player.teleport(island.getSpawn());
            getMessages().sendTo(player, "command.island.create.island_created");
        });
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
