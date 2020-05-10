package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.service.PlayerService;

@PluginCommand(command = "spawn")
public class SpawnCommand extends BaseCommand {

    private final PlayerService playerService;

    @Autowired
    public SpawnCommand(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        playerService.teleportToSpawn(player);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
