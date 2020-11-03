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
import pl.subtelny.islands.islandold.IslandRemoveService;
import pl.subtelny.islands.islander.IslanderService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.IntegerUtil;
import pl.subtelny.utilities.Validation;

@PluginSubCommand(command = "delete", aliases = "usun", mainCommand = IslandCommand.class)
public class IslandDeleteCommand extends BaseCommand {

    private final IslandRemoveService islandRemover;

    private final ConfirmationService confirmationService;

    private final IslanderService islanderService;

    @Autowired
    public IslandDeleteCommand(IslandMessages messages,
                               IslanderService islanderService,
                               IslandRemoveService islandRemover,
                               ConfirmationService confirmationService) {
        super(messages);
        this.islanderService = islanderService;
        this.islandRemover = islandRemover;
        this.confirmationService = confirmationService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);

        int island = 0;
        if (args.length > 0) {
            String arg = args[0];
            if (IntegerUtil.isInt(arg)) {
                island = Integer.parseInt(arg);
            }
        }

        removeIsland(player, islander, island);
    }

    private void removeIsland(Player player, Islander islander, int islandPosition) {
        validateIslandPosition(islander, islandPosition);
        Island island = islander.getIslands().get(islandPosition);

        String contextIdRaw = String.join("@", "island.remove", player.getName());
        String title = getMessages().getColoredFormattedMessage("command.island.delete.confirmation_title", island.getType().getInternal());

        ConfirmContextId confirmContextId = ConfirmContextId.of(contextIdRaw);

        ConfirmationRequest request = ConfirmationRequest.builder(confirmContextId, player)
                .stateListener(getListener(island))
                .title(title)
                .build();
        confirmationService.makeConfirmation(request);
    }

    private Callback<Boolean> getListener(Island island) {
        return state -> {
            if (state) {
                islandRemover.removeIsland(island);
            }
        };
    }

    private void validateIslandPosition(Islander islander, int islandPosition) {
        Validation.isTrue(islander.getIslands().size() >= islandPosition, "command.island.delete.island_not_found");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
