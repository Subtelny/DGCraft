package pl.subtelny.test;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.utilities.MessageUtil;

@PluginSubCommand(
        command = "test",
        mainCommand = IslandCommand.class
)
public class CreateCommand extends BaseCommand {

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        MessageUtil.message(sender, "asdsa");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }

}
