package pl.subtelny.test;

import org.bukkit.command.CommandSender;
import pl.subtelny.beans.command.SubCommand;
import pl.subtelny.command.BaseCommand;
import pl.subtelny.utils.MessageUtil;

@SubCommand(
        baseCommand = IslandCommand.class,
        command = "stworz"
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
