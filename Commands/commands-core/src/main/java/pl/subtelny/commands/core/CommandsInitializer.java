package pl.subtelny.commands.core;

import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.utilities.exception.ValidationException;

import java.lang.annotation.Annotation;
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
        List<BaseCommand> mainCommands =
                getCommandsByAndAnnotation(PluginCommand.class);
        mainCommands.forEach(this::registerAsMainCommand);

        List<BaseCommand> subCommands =
                getCommandsByAndAnnotation(PluginSubCommand.class);
        subCommands.forEach(this::registerAsSubCommand);
    }

    private List<BaseCommand> getCommandsByAndAnnotation(Class<? extends Annotation> annotation) {
        return commands.stream()
                .filter(command -> command.getClass().isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private void registerAsMainCommand(BaseCommand baseCommand) {
        PluginCommand pluginCommand = baseCommand.getClass().getAnnotation(PluginCommand.class);
        pluginCommands.registerCommand(pluginCommand.command(), Arrays.asList(pluginCommand.aliases()), baseCommand);
    }

    private void registerAsSubCommand(BaseCommand baseCommand) {
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