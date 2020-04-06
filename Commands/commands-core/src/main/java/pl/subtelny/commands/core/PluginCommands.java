package pl.subtelny.commands.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import pl.subtelny.commands.api.BukkitCommandAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PluginCommands {

    private final Plugin plugin;

    public PluginCommands(Plugin plugin) {
        this.plugin = plugin;
    }

    public PluginCommand registerCommand(String cmd, List<String> aliases, BukkitCommandAdapter adapter) {
        PluginCommand command = getCommand(cmd, plugin);
        command.setExecutor(adapter);
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
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return commandMap;
    }

}