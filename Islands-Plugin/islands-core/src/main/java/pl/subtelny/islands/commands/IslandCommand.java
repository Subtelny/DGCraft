package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.message.IslandMessages;

@PluginCommand(command = "island", aliases = {"wyspa", "w", "is"})
public class IslandCommand extends BaseCommand {

    private final IslandMessages islandMessages;

    @Autowired
    public IslandCommand(IslandMessages islandMessages) {
        this.islandMessages = islandMessages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        islandMessages.sendTo(sender, "island.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
