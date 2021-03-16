package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.api.confirmation.PlayerConfirmable;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.command.IslandCommandService;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.ImprovedPlayer;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import javax.annotation.Nullable;
import java.util.List;

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
                               IslandCommandService islandCommandService,
                               IslandQueryService islandQueryService) {
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

        IslandType islandType = IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE;
        removeIsland(player, islander, islandType);
    }

    private void removeIsland(Player player, Islander islander, IslandType islandType) {
        List<IslandId> islands = islander.getIslands(islandType);
        Validation.isFalse(islands.isEmpty(), "command.island.delete.not_have_island");
        IslandId islandId = islands.get(0);
        Island island = getIsland(islandId);
        removeIsland(player, islander, island);
    }

    private void removeIsland(Player player, Islander islander, Island island) {
        Validation.isTrue(island.isOwner(islander), "command.island.delete.not_owner");
        makeConfirmation(player, island);
    }

    private Island getIsland(IslandId islandId) {
        return islandQueryService.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("command.island.delete.island_not_found", islandId.getInternal()));
    }

    private void makeConfirmation(Player player, Island island) {
        String contextIdRaw = String.join("@", "island.remove", player.getName());
        String title = getMessages().getColoredFormattedMessage("command.island.delete.confirmation_title", island.getIslandType().getInternal());

        ConfirmationRequest request = ConfirmationRequest.builder(contextIdRaw, ImprovedPlayer.of(player), new PlayerConfirmable(player))
                .stateListener(getListener(player, island))
                .title(title)
                .build();
        confirmationService.makeConfirmation(request);
    }

    private Callback<Boolean> getListener(Player player, Island island) {
        return state -> {
            if (state) {
                removeIsland(player, island);
            }
        };
    }

    private void removeIsland(Player player, Island island) {
        getMessages().sendTo(player, "command.island.delete.removing_island");
        islandCommandService.removeIsland(island)
                .whenComplete((o, throwable) -> handleResult(player, throwable));
    }

    private void handleResult(Player player, @Nullable Throwable throwable) {
        if (throwable == null) {
            getMessages().sendTo(player, "command.island.delete.island_removed");
        } else {
            if (throwable instanceof ValidationException) {
                String message = throwable.getMessage();
                getMessages().sendTo(player, message, ((ValidationException) throwable).getValues());
            } else {
                getMessages().sendTo(player, "command.island.delete.error");
                throwable.printStackTrace();
            }
        }
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
