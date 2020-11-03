package pl.subtelny.commands.api;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.subtelny.utilities.exception.ValidationException;

public final class BukkitCommandAdapter implements CommandExecutor {

    private final BaseCommand baseCommand;

    public BukkitCommandAdapter(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        try {
            baseCommand.executeCommand(sender, args);
        } catch (ValidationException e) {
            baseCommand.getMessages().sendTo(sender, e.getMessage(), e.getValues());
        }
        return true;
    }

}
