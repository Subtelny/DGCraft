package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.commands.coredev.CoreDevCommand;
import pl.subtelny.core.configuration.CoreMessages;

@PluginSubCommand(command = "setspawn", mainCommand = CoreDevCommand.class)
public class CoreDevSetSpawnCommand extends BaseCommand {

    private final CoreMessages messages;

    @Autowired
    public CoreDevSetSpawnCommand(CoreMessages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        messages.sendTo(sender, "coredev.setspawn.usage");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
