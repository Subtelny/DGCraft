package pl.subtelny.core.commands.coredev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.CoreMessages;

@PluginCommand(command = "coredev")
public class CoreDevCommand extends BaseCommand {

    private final CoreMessages messages;

    @Autowired
    public CoreDevCommand(CoreMessages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        messages.sendTo(sender, "coredev.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
