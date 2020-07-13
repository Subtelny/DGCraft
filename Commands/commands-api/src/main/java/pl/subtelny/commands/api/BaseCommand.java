package pl.subtelny.commands.api;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.log.LogUtil;
import pl.subtelny.utilities.messages.Messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BaseCommand implements Command {

    private String permission;

    private Map<String, Command> subCommands = new HashMap<>();

    private final Messages messages;

    protected BaseCommand(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        validatePlayerOnlyUsage(sender);
        validatePermission(sender);

        Optional<Command> nextCommand = findNextCommand(args);
        if (nextCommand.isEmpty()) {
            handleCommand(sender, args);
        } else {
            String[] argsWithoutCommand = Arrays.copyOfRange(args, 1, args.length);
            nextCommand.get().executeCommand(sender, argsWithoutCommand);
        }
    }

    public void registerSubCommand(String command, Command subCommand) {
        subCommands.put(command, subCommand);
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Messages getMessages() {
        return messages;
    }

    public abstract void handleCommand(CommandSender sender, String[] args);

    private void validatePlayerOnlyUsage(CommandSender sender) {
        if (isPlayerOnlyUsage()) {
            if (!(sender instanceof Player)) {
                throw ValidationException.of("command.only_for_players");
            }
        }
    }

    private void validatePermission(CommandSender sender) {
        if (StringUtils.isNotBlank(permission)) {
            if (!sender.hasPermission(permission)) {
                LogUtil.warning(sender.getName() + " has no permission for command " + this.getClass().getName());
                throw ValidationException.of("command.no_permission_for_command");
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
        return Optional.ofNullable(subCommands.get(command));
    }
}
