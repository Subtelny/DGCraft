package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(
        mainCommand = AuthorCommand.class,
        command = "test"
)
public class TestCommand extends BaseCommand {

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, "&fTest");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
