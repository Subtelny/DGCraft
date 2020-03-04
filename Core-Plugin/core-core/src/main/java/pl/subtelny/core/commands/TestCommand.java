package pl.subtelny.core.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.command.BaseCommand;
import pl.subtelny.utils.MessageUtil;

//@SubCommand(
//        baseCommand = AuthorCommand.class,
//        command = "test"
//)
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
