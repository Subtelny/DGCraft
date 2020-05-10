package pl.subtelny.core.commands.coredev;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

@PluginCommand(command = "coredev")
public class CoreDevCommand extends BaseCommand {

    private final Messages messages;

    @Autowired
    public CoreDevCommand(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, messages.get("coredev.usage"));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
