package pl.subtelny.commands.api;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.plugin.Plugin;
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

        ((CraftServer) Bukkit.getServer()).syncCommands();
    }

    private List<BaseCommand> getCommandsByAndAnnotation(Class<? extends Annotation> annotation) {
        return commands.stream()
                .filter(command -> command.getClass().isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private void registerAsMainCommand(BaseCommand baseCommand) {
        PluginCommand pluginCommand = baseCommand.getClass().getAnnotation(PluginCommand.class);
        baseCommand.setPermission(pluginCommand.permission());
        pluginCommands.registerCommand(pluginCommand.command(), Arrays.asList(pluginCommand.aliases()), baseCommand);
    }

    private void registerAsSubCommand(BaseCommand baseCommand) {
        PluginSubCommand pluginSubCommand = baseCommand.getClass().getAnnotation(PluginSubCommand.class);
        Class mainCommand = pluginSubCommand.mainCommand();

        BaseCommand commandByClass = getCommandByClass(mainCommand);
        baseCommand.setPermission(pluginSubCommand.permission());
        commandByClass.registerSubCommand(pluginSubCommand.command(), baseCommand);
        for (String alias : pluginSubCommand.aliases()) {
            commandByClass.registerSubCommand(alias, baseCommand);
        }
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
