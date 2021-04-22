package pl.subtelny.islands.commands.dev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.island.message.IslandMessages;

@PluginCommand(command = "islanddev", aliases = {"wyspadev", "wdev", "isdev"})
public class IslandDevCommand extends BaseCommand {

    @Autowired
    public IslandDevCommand(IslandMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        getMessages().sendTo(sender, "command.islanddev.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
