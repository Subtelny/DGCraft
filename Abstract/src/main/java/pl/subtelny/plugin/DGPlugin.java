package pl.subtelny.plugin;

import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.subtelny.beans.BeanContext;
import pl.subtelny.beans.BeanLoader;
import pl.subtelny.beans.command.HeadCommand;
import pl.subtelny.command.BukkitCommandAdapter;

public abstract class DGPlugin extends JavaPlugin {

	public static Plugin plugin;

	@Override
    public void onLoad() {
		plugin = this;
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

}
