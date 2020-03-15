package pl.subtelny.commands.core;

import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.BukkitCommandAdapter;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsInitializer {

    private final PluginCommands pluginCommands;

    private final List<BaseCommand> commands;

    public CommandsInitializer(Plugin plugin, List<BaseCommand> commands) {
        this.pluginCommands = new PluginCommands(plugin);
        this.commands = commands;
    }

    public void registerCommands() {
        List<BukkitCommandAdapter> mainCommands =
                getCommandsByClassAndAnnotation(BukkitCommandAdapter.class, PluginCommand.class);
        mainCommands.forEach(this::registerMainCommand);

        List<BaseCommand> subCommands =
                getCommandsByClassAndAnnotation(BaseCommand.class, PluginSubCommand.class);
        subCommands.forEach(this::registerSubCommand);
    }

    private <T> List<T> getCommandsByClassAndAnnotation(Class<T> clazz, Class annotation) {
        return commands.stream()
                .filter(command -> command.getClass().isAssignableFrom(clazz))
                .filter(command -> command.getClass().isAnnotationPresent(annotation))
                .map(command -> (T) command)
                .collect(Collectors.toList());
    }

    private void registerMainCommand(BukkitCommandAdapter adapter) {
        PluginCommand pluginCommand = adapter.getClass().getAnnotation(PluginCommand.class);
        org.bukkit.command.PluginCommand registeredCommand = pluginCommands
                .registerCommand(pluginCommand.command(), Arrays.asList(pluginCommand.aliases()));
        registeredCommand.setExecutor(adapter);
    }

    private void registerSubCommand(BaseCommand baseCommand) {
        PluginSubCommand pluginSubCommand = baseCommand.getClass().getAnnotation(PluginSubCommand.class);
        Class mainCommand = pluginSubCommand.mainCommand();

        BaseCommand commandByClass = getCommandByClass(mainCommand);
        commandByClass.registerSubCommand(pluginSubCommand.command(), baseCommand);
    }

    private BaseCommand getCommandByClass(Class mainCommand) {
        return commands.stream()
                .filter(command -> command.getClass().isAssignableFrom(mainCommand))
                .findAny()
                .orElseThrow(() -> {
                    String message = String.format("Not found command by class %s", mainCommand.getName());
                    return ValidationException.of(message);
                });
    }

}
