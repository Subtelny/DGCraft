package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.message.IslandMessages;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;

@PluginCommand(command = "island", aliases = {"wyspa", "w", "is"})
public class IslandCommand extends AbstractIslandOpenCrateCommand {

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandCommand(IslandMessages messages,
                         IslanderQueryService islanderQueryService,
                         IslandQueryService islandQueryService) {
        super(messages, islanderQueryService, islandQueryService);
        this.islanderQueryService = islanderQueryService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderQueryService.getIslander(player);
        IslandType seasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;

        if (islander.hasIsland(seasonIslandType)) {
            openCrateBasedOnIsland(seasonIslandType, player, "main");
            return;
        }
        openCrate(seasonIslandType, player, "main");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
