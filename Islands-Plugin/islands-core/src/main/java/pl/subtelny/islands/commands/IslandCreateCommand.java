package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandCreateRequest;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.log.LogUtil;

import java.util.Optional;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslanderQueryService islanderService;

    private final PlayerCrateSessionService sessionService;

    private final IslandModules islandModules;

    @Autowired
    public IslandCreateCommand(IslandMessages messages,
                               IslanderQueryService islanderService,
                               PlayerCrateSessionService sessionService,
                               IslandModules islandModules) {
        super(messages);
        this.islanderService = islanderService;
        this.sessionService = sessionService;
        this.islandModules = islandModules;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);

        if (args.length == 0) {
            return;
        }

        IslandType islandType = new IslandType(args[0]);
        Optional<IslandModule<Island>> islandModuleOpt = islandModules.findIslandModule(islandType);
        islandModuleOpt.ifPresent(islandIslandModule -> {
            IslandCreateRequest request = IslandCreateRequest.newBuilder()
                    .addParameter("owner", islander)

                    .build();
            Island island = islandIslandModule.createIsland(request);

        });
    }


    private void islandFailureCreate(Player player, Throwable throwable) {
        LogUtil.warning("Error while creating island: " + throwable.getMessage());
        throwable.printStackTrace();
        getMessages().sendTo(player, "command.island.create.internal_error");
    }

    private void islandSucessfullyCreated(Player player, SkyblockIsland skyblockIsland) {
        JobsProvider.runSync(Islands.plugin, () -> {
            player.teleport(skyblockIsland.getSpawn());
            getMessages().sendTo(player, "command.island.create.island_created");
        });
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
