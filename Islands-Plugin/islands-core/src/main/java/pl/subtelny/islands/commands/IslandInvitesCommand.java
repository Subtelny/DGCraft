package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.islander.IslanderQueryService;

@PluginSubCommand(command = "zaproszenia", aliases = {"invites", "invs"}, mainCommand = IslandCommand.class)
public class IslandInvitesCommand extends AbstractIslandOpenCrateCommand {

    @Autowired
    public IslandInvitesCommand(IslandMessages messages,
                                IslanderQueryService islanderQueryService,
                                IslandModules islandModules) {
        super(messages, islanderQueryService, islandModules);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        IslandType seasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        Player player = (Player) sender;
        openCrateBasedOnIsland(seasonIslandType, player, "invites");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
