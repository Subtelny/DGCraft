package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.exception.ValidationException;

import java.time.ZoneId;

@PluginCommand(command = "island", aliases = {"wyspa", "w", "is"})
public class IslandCommand extends BaseCommand {

    private final IslandModules islandModules;

    private final IslanderQueryService islanderQueryService;

    @Autowired
    public IslandCommand(IslandMessages messages,
                         IslandModules islandModules,
                         IslanderQueryService islanderQueryService) {
        super(messages);
        this.islandModules = islandModules;
        this.islanderQueryService = islanderQueryService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderQueryService.getIslander(player);
        IslandType seasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;

        if (islander.hasIsland(seasonIslandType)) {
            //openMainCrate(player);
            //return;
        }

        openCreateCrate(player);
    }

    private void openMainCrate(Player sender) {
        IslandCrates islandCrates = getIslandCrates();
        islandCrates.openMainCrate(sender);
    }

    private void openCreateCrate(Player sender) {
        IslandCrates islandCrates = getIslandCrates();
        islandCrates.openCreateCrate(sender);
    }

    private IslandCrates getIslandCrates() {
        IslandType seasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        return islandModules.findIslandModule(seasonIslandType)
                .map(IslandModule::getIslandCrates)
                .orElseThrow(() -> ValidationException.of("command.island.island_module_not_found", seasonIslandType));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
