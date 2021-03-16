package pl.subtelny.islands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.island.cqrs.command.IslandInviteService;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

import java.util.Optional;

@PluginSubCommand(command = "popros", aliases = "ask", mainCommand = IslandCommand.class)
public class IslandAskCommand extends BaseCommand {

    private final IslandInviteService islandInviteService;

    private final Messages messages;

    @Autowired
    public IslandAskCommand(IslandMessages messages,
                            IslandInviteService islandInviteService) {
        super(messages);
        this.messages = messages;
        this.islandInviteService = islandInviteService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            usage(sender);
        } else {
            Player player = (Player) sender;
            Player playerToJoin = getPlayer(args[0]);
            islandInviteService.ask(player, playerToJoin);
        }
    }

    private Player getPlayer(String value) {
        Player player = Bukkit.getPlayer(value);
        return Optional.ofNullable(player)
                .orElseThrow(() -> ValidationException.of("command.island.player_not_found", value));
    }

    private void usage(CommandSender sender) {
        messages.sendTo(sender, "command.island.ask.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
