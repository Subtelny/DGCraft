package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;

@PluginCommand(command = "coredev")
public class CoreCommand extends BaseCommand {

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        sender.sendMessage("asd");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
