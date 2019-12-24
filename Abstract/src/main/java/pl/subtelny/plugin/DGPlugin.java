package pl.subtelny.plugin;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.subtelny.beans.BeanContext;
import pl.subtelny.beans.BeanLoader;
import pl.subtelny.beans.command.HeadCommand;
import pl.subtelny.command.BukkitCommandAdapter;
import pl.subtelny.utils.plugin.PluginCommands;

import java.util.List;

public abstract class DGPlugin extends JavaPlugin {

    private static TaskChainFactory taskChainFactory;

    @Override
    public void onLoad() {
        taskChainFactory = BukkitTaskChainFactory.create(this);
        onLoaded();
        initializeBeans();
    }

    private void initializeBeans() {
        String path = this.getClass().getPackage().getName();
        BeanLoader beanLoader = new BeanLoader(path);
        beanLoader.initializeBeans();
    }

    @Override
    public void onEnable() {
        initializeListeners();
        initializeCommands();
        onEnabled();
    }

    private void initializeCommands() {
        List<BukkitCommandAdapter> bukkitAdapterCommands = loadBukkitCommands();

        PluginCommands pluginCommands = new PluginCommands(this);
        bukkitAdapterCommands.stream()
                .filter(i -> i.getClass().isAnnotationPresent(HeadCommand.class))
                .forEach(adapter -> {
                    HeadCommand pluginCommand = adapter.getClass().getAnnotation(HeadCommand.class);
                    List<String> commands = Lists.asList(pluginCommand.command(), pluginCommand.aliases());
                    PluginCommand bukkitCommand = pluginCommands.registerCommand(commands);
                    bukkitCommand.setExecutor(adapter);
                });
    }

    private List<BukkitCommandAdapter> loadBukkitCommands() {
        return BeanContext.getBeans(BukkitCommandAdapter.class);
    }

    private void initializeListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        List<Listener> listeners = loadListeners();
        listeners.forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    private List<Listener> loadListeners() {
        return BeanContext.getBeans(Listener.class);
    }

    public abstract void onLoaded();

    public abstract void onEnabled();

    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }
    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }
}
