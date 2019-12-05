package pl.subtelny.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.subtelny.utils.MessageUtil;

public abstract class BukkitCommandAdapter extends BaseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        try {
            executeCommand(sender, args);
        } catch (CommandException e) {
            MessageUtil.error(sender, e.getMessage());
        }
        return true;
    }

}
