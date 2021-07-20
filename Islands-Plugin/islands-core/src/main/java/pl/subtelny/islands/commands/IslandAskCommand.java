package pl.subtelny.islands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.cqrs.command.IslandInviteService;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.utilities.IntegerUtil;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@PluginSubCommand(command = "zapytaj", aliases = "ask", mainCommand = IslandCommand.class)
public class IslandAskCommand extends BaseCommand {

    private final IslandInviteService islandInviteService;

    @Autowired
    public IslandAskCommand(IslandInviteService islandInviteService) {
        super(IslandMessages.get());
        this.islandInviteService = islandInviteService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            usage(sender);
        } else {
            Player player = (Player) sender;
            String arg = args[0];

            if (IntegerUtil.isInt(arg)) {
                IslandId islandId = IslandId.of(Integer.valueOf(arg), IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE);
                islandInviteService.ask(player, islandId);
                return;
            }

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
        IslandMessages.get().sendTo(sender, "command.island.ask.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
