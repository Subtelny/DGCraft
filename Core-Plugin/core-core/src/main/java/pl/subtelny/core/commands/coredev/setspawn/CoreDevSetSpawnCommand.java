package pl.subtelny.core.commands.coredev.setspawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.commands.coredev.CoreDevCommand;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.utilities.MessageUtil;
import pl.subtelny.utilities.PlayerUtil;

@PluginSubCommand(command = "setspawn", mainCommand = CoreDevCommand.class)
public class CoreDevSetSpawnCommand extends BaseCommand {

    private final Messages messages;

    @Autowired
    public CoreDevSetSpawnCommand(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, messages.get("coredev.setspawn.usage"));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
