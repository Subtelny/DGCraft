package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.service.IslanderService;
import pl.subtelny.utilities.exception.ValidationException;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PluginSubCommand(command = "create", aliases = "stworz", mainCommand = IslandCommand.class)
public class IslandCreateCommand extends BaseCommand {

    private final IslandMessages islandMessages;

    private final IslanderService islanderService;

    @Autowired
    public IslandCreateCommand(IslandMessages islandMessages, IslanderService islanderService) {
        this.islandMessages = islandMessages;
        this.islanderService = islanderService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        validateIslandExists(player);

    }

    private void validateIslandExists(Player player) {
        Islander islander = islanderService.getIslander(player);
        Optional<IslandId> skyblockIslandOpt = islander.getSkyblockIsland();
        skyblockIslandOpt.ifPresent(islandId -> {
            throw ValidationException.of(islandMessages.getRawMessage("island.create.already_has_island"));
        });
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
