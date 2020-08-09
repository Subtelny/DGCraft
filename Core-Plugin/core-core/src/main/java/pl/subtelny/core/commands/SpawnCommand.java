package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.player.CorePlayer;
import pl.subtelny.core.player.CorePlayerService;

@PluginCommand(command = "spawn")
public class SpawnCommand extends BaseCommand {

    private final CorePlayerService corePlayerService;

    @Autowired
    public SpawnCommand(CoreMessages messages, CorePlayerService corePlayerService) {
        super(messages);
        this.corePlayerService = corePlayerService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        CorePlayer corePlayer = corePlayerService.getCorePlayer(player);
        corePlayer.respawn();
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
