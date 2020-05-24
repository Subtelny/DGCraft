package pl.subtelny.core.commands.coredev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;

@PluginSubCommand(command = "reload", mainCommand = CoreDevCommand.class)
public class CoreDevReloadCommand extends BaseCommand {

    private final CoreMessages messages;

    @Autowired
    public CoreDevReloadCommand(CoreMessages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        messages.reloadMessages();
        messages.sendTo(sender, "config_reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
