package pl.subtelny.gui.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.messages.CrateMessages;

@PluginCommand(command = "cratedev")
public class CrateDevCommand extends BaseCommand {

    @Autowired
    public CrateDevCommand(CrateMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender commandSender, String[] strings) {
        getMessages().sendTo(commandSender, "command.cratedev.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
