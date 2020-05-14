package pl.subtelny.commands.api;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.subtelny.utilities.MessageUtil;
import pl.subtelny.utilities.exception.ValidationException;

public final class BukkitCommandAdapter implements CommandExecutor {

    private final CommandMessages commandMessages;

    private final BaseCommand baseCommand;

    public BukkitCommandAdapter(CommandMessages commandMessages, BaseCommand baseCommand) {
        this.commandMessages = commandMessages;
        this.baseCommand = baseCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        try {
            baseCommand.executeCommand(sender, args);
        } catch (CommandException e) {
            MessageUtil.message(sender, commandMessages.get(e.getMessage()));
        } catch (ValidationException e) {
            MessageUtil.message(sender, String.format(commandMessages.get(e.getMessage()), e.getValues()));
        }
        return true;
    }

}
