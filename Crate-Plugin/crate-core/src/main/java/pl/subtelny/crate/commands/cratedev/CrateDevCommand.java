package pl.subtelny.crate.commands.cratedev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messages;

@PluginCommand(command = "cratedev", permission = "dgcraft.cratedev")
public class CrateDevCommand extends BaseCommand {

    @Autowired
    public CrateDevCommand(CrateMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        throw ValidationException.of("command.cratedev.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
