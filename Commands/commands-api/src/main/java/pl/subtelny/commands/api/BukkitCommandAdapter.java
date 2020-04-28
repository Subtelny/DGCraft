package pl.subtelny.commands.api;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.subtelny.utilities.MessageUtil;

public final class BukkitCommandAdapter implements CommandExecutor {

    private final BaseCommand baseCommand;

    public BukkitCommandAdapter(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        try {
            baseCommand.executeCommand(sender, args);
        } catch (CommandException e) {
            MessageUtil.message(sender, e.getMessage());
        }
        return true;
    }

}
