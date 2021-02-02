package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

@PluginSubCommand(command = "zaproszenia", aliases = {"invites", "invs"}, mainCommand = IslandCommand.class)
public class IslandInvitesCommand extends BaseCommand {

    private final IslandModules islandModules;

    private final IslanderQueryService islanderQueryService;

    private final IslandQueryService islandQueryService;

    @Autowired
    public IslandInvitesCommand(IslandMessages messages, IslandModules islandModules,
                                IslanderQueryService islanderQueryService,
                                IslandQueryService islandQueryService) {
        super(messages);
        this.islandModules = islandModules;
        this.islanderQueryService = islanderQueryService;
        this.islandQueryService = islandQueryService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        IslandType seasonIslandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        Player player = (Player) sender;
        openInvitesCrate(seasonIslandType, player);
    }

    private void openInvitesCrate(IslandType seasonIslandType, Player player) {
        Islander islander = getIslander(player);
        Island island = getIsland(islander, seasonIslandType);

        IslandModule<Island> islandModule = getIslandModule(seasonIslandType);
        IslandCrates islandCrates = islandModule.getIslandCrates();
        islandCrates.openInvitesCrate(player, island);
    }

    private Island getIsland(Islander islander, IslandType islandType) {
        Validation.isTrue(islander.hasIsland(islandType), "command.island.island_not_found", islandType.getInternal());
        IslandId islandId = islander.getIslands(islandType).get(0);
        return islandQueryService.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("command.island.island_not_found", islandId.getInternal()));
    }

    private Islander getIslander(Player player) {
        return islanderQueryService.getIslander(player);
    }

    private IslandModule<Island> getIslandModule(IslandType seasonIslandType) {
        return islandModules.findIslandModule(seasonIslandType)
                .orElseThrow(() -> ValidationException.of("command.island.island_module_not_found", seasonIslandType));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
