package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandCommandService;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.query.IslandQueryService;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

@PluginSubCommand(command = "delete", aliases = "usun", mainCommand = IslandCommand.class)
public class IslandDeleteCommand extends BaseCommand {

    private final ConfirmationService confirmationService;

    private final IslanderQueryService islanderService;

    private final IslandCommandService islandCommandService;

    private final IslandQueryService islandQueryService;

    @Autowired
    public IslandDeleteCommand(IslandMessages messages,
                               IslanderQueryService islanderService,
                               ConfirmationService confirmationService,
                               IslandCommandService islandCommandService, IslandQueryService islandQueryService) {
        super(messages);
        this.islanderService = islanderService;
        this.confirmationService = confirmationService;
        this.islandCommandService = islandCommandService;
        this.islandQueryService = islandQueryService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);

        IslandType islandType = new IslandType(args[0]);

        removeIsland(player, islander, islandType);
    }

    private void removeIsland(Player player, Islander islander, IslandType islandType) {
        Validation.isTrue(islander.hasIsland(islandType), "command.island.delete.not_have_island");
        IslandId islandId = islander.getIslands(islandType).get(0);
        Island island = getIsland(islandId);
        Validation.isTrue(isOwner(islander, island), "command.island.delete.not_owner_of_island");

        makeConfirmation(player, island);
    }

    private void makeConfirmation(Player player, Island island) {
        String contextIdRaw = String.join("@", "island.remove", player.getName());
        String title = getMessages().getColoredFormattedMessage("command.island.delete.confirmation_title", island.getIslandType().getInternal());

        ConfirmContextId confirmContextId = ConfirmContextId.of(contextIdRaw);
        ConfirmationRequest request = ConfirmationRequest.builder(confirmContextId, player)
                .stateListener(getListener(island))
                .title(title)
                .build();
        confirmationService.makeConfirmation(request);
    }

    private boolean isOwner(Islander islander, Island island) {
        return islander.equals(island.getOwner().orElse(null));
    }

    private Island getIsland(IslandId islandId) {
        return islandQueryService.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("command.island.delete.island_not_found"));
    }

    private Callback<Boolean> getListener(Island island) {
        return state -> {
            if (state) {
                islandCommandService.removeIsland(island);
            }
        };
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
