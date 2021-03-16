package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.membership.IslandMembershipService;
import pl.subtelny.islands.message.IslandMessages;

@PluginSubCommand(command = "wyjdz", aliases = "leave", mainCommand = IslandCommand.class)
public class IslandLeaveCommand extends BaseCommand {

    private final IslandMembershipService islandMembershipService;

    @Autowired
    public IslandLeaveCommand(IslandMessages messages,
                              IslandMembershipService islandMembershipService) {
        super(messages);
        this.islandMembershipService = islandMembershipService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        islandMembershipService.leave(player, IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
