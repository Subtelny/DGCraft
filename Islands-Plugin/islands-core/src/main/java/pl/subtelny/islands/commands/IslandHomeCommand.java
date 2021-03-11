package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.exception.ValidationException;

@PluginSubCommand(command = "dom", aliases = "home", mainCommand = IslandCommand.class)
public class IslandHomeCommand extends BaseCommand {

    private final IslanderQueryService islanderService;

    private final IslandQueryService islandQueryService;

    @Autowired
    public IslandHomeCommand(IslandMessages messages,
                             IslanderQueryService islanderService,
                             IslandQueryService islandQueryService) {
        super(messages);
        this.islanderService = islanderService;
        this.islandQueryService = islandQueryService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);
        islander.getIslands(IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE)
                .stream()
                .findFirst()
                .ifPresentOrElse(islandId -> teleportToIsland(player, islandId), () -> notHaveIsland(player));
    }

    private void notHaveIsland(Player player) {
        getMessages().sendTo(player, "command.island.home.not_have_island");
    }

    private void teleportToIsland(Player player, IslandId islandId) {
        Island island = getIsland(islandId);
        player.setVelocity(new Vector());
        player.setFallDistance(0);
        player.teleport(island.getSpawn());
    }

    private Island getIsland(IslandId islandId) {
        return islandQueryService.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("command.island.home.island_not_found", islandId));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
