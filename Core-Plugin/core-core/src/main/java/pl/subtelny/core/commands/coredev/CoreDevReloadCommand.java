package pl.subtelny.core.commands.coredev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.messages.Messages;

@PluginSubCommand(command = "reload", mainCommand = CoreDevCommand.class)
public class CoreDevReloadCommand extends BaseCommand {

    @Autowired
    public CoreDevReloadCommand(CoreMessages messages) {
        super(messages);
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Messages messages = getMessages();
        messages.reloadMessages();
        messages.sendTo(sender, "command.citydev.config_reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
