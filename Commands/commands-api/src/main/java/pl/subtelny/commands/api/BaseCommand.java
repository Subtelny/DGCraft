package pl.subtelny.commands.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BaseCommand implements Command {

    private Map<String, Command> subCommands = new HashMap<>();

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        validatePlayerOnlyUsage(sender);

        Optional<Command> nextCommand = findNextCommand(args);
        if (nextCommand.isEmpty()) {
            handleCommand(sender, args);
        } else {
            String[] argsWithoutCommand = Arrays.copyOfRange(args, 1, args.length);
            nextCommand.get().executeCommand(sender, argsWithoutCommand);
        }
        return false;
    }

    public void registerSubCommand(String command, Command subCommand) {
        subCommands.put(command, subCommand);
    }

    public abstract void handleCommand(CommandSender sender, String[] args);

    private void validatePlayerOnlyUsage(CommandSender sender) {
        if (isPlayerOnlyUsage()) {
            if (!(sender instanceof Player)) {
                throw new CommandException("Komenda dostepna tylko dla graczy");
            }
        }
    }

    private Optional<Command> findNextCommand(String[] args) {
        if (args.length == 0) {
            return Optional.empty();
        }
        String nextArg = args[0];
        return getSubCommand(nextArg);
    }

    private Optional<Command> getSubCommand(String command) {
        return Optional.of(subCommands.get(command));
    }

}
