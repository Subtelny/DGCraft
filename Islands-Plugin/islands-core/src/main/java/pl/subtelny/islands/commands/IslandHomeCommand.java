package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.islander.IslanderService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;

@PluginSubCommand(command = "dom", aliases = "home", mainCommand = IslandCommand.class)
public class IslandHomeCommand extends BaseCommand {

    private final IslanderService islanderService;

    @Autowired
    public IslandHomeCommand(IslandMessages messages, IslanderService islanderService) {
        super(messages);
        this.islanderService = islanderService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);
        islander.getIslands().stream().findFirst()
                .ifPresentOrElse(island -> teleportToIsland(player, island), () -> notHaveIsland(player));
    }

    private void notHaveIsland(Player player) {
        getMessages().sendTo(player, "command.island.home.not_have_island");
    }

    private void teleportToIsland(Player player, Island island) {
        player.teleport(island.getSpawn());
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
