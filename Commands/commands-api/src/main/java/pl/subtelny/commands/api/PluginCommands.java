package pl.subtelny.commands.api;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PluginCommands {

    private final Plugin plugin;

    public PluginCommands(Plugin plugin) {
        this.plugin = plugin;
    }

    public PluginCommand registerCommand(String cmd, List<String> aliases, BaseCommand baseCommand) {
        PluginCommand command = getCommand(cmd, plugin);
        command.setExecutor(new BukkitCommandAdapter(baseCommand));
        command.setAliases(aliases);
        getCommandMap().register(plugin.getDescription().getName(), command);
        return command;
    }

    private PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);
            command = c.newInstance(name, plugin);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return command;
    }

    private CommandMap getCommandMap() {
        return Bukkit.getCommandMap();
    }

}