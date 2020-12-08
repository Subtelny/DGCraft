package pl.subtelny.islands.commands.dev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.message.IslandMessages;

@PluginSubCommand(command = "reload", aliases = "reload", mainCommand = IslandDevCommand.class)
public class IslandDevReloadCommand extends BaseCommand {

    @Autowired
    public IslandDevReloadCommand(IslandMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        getMessages().sendTo(sender, "command.islanddev.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
