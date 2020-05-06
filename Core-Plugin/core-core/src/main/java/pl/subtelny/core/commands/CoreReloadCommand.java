package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "reload", mainCommand = CoreCommand.class)
public class CoreReloadCommand extends BaseCommand {

    private final Messages messages;

    @Autowired
    public CoreReloadCommand(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        messages.reloadMessages();
        MessageUtil.message(sender, messages.get("config_reloaded"));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
