package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.command.IslandDeleteService;
import pl.subtelny.islands.message.IslandMessages;

@PluginSubCommand(command = "delete", aliases = "usun", mainCommand = IslandCommand.class)
public class IslandDeleteCommand extends BaseCommand {

    private final IslandDeleteService islandRemoveService;

    @Autowired
    public IslandDeleteCommand(IslandMessages messages, IslandDeleteService islandRemoveService) {
        super(messages);
        this.islandRemoveService = islandRemoveService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        IslandType islandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        islandRemoveService.deleteIsland(player, islandType);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
