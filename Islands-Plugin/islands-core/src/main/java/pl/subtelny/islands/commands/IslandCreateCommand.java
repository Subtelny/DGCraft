package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.api.cqrs.command.IslandCreateService;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.message.IslandMessages;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslandCreateService islandCreateService;

    @Autowired
    public IslandCreateCommand(IslandMessages messages,
                               IslandCreateService islandCreateService) {
        super(messages);
        this.islandCreateService = islandCreateService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        createIsland(player);
    }

    private void createIsland(Player player) {
        IslandType actualSeasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        islandCreateService.createIsland(player, actualSeasonIslandType);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
