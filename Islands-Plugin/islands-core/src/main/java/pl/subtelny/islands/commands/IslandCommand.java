package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.message.IslandMessages;

@PluginCommand(command = "island", aliases = {"wyspa", "w", "is"})
public class IslandCommand extends BaseCommand {

    @Autowired
    public IslandCommand(IslandMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        getMessages().sendTo(sender, "command.island.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
