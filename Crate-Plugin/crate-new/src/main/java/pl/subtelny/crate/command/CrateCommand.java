package pl.subtelny.crate.command;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.crate.messages.CrateMessages;

@PluginCommand(command = "crate", permission = "dgcraft.crate.use")
public class CrateCommand extends BaseCommand {

    @Autowired
    public CrateCommand(CrateMessages crateMessages) {
        super(crateMessages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {

    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
