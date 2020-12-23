package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.exception.ValidationException;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslanderQueryService islanderService;

    @Autowired
    public IslandCreateCommand(IslandMessages messages,
                               IslanderQueryService islanderService) {
        super(messages);
        this.islanderService = islanderService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);

        IslandType actualSeasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        if (islander.hasIsland(actualSeasonIslandType)) {
            throw ValidationException.of("command.island.create.out_of_island_type_size");
        }
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
