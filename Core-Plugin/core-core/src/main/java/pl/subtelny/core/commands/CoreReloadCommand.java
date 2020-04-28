package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(command = "reload", mainCommand = CoreCommand.class)
public class CoreReloadCommand extends BaseCommand {

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Messages.reloadMessages();
        MessageUtil.message(sender, Messages.get("config_reloaded"));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }

}
