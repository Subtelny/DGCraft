package pl.subtelny.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.subtelny.commands.api.context.CommandsContext;
import pl.subtelny.components.core.api.BeanContext;
import pl.subtelny.components.core.api.BeanContextId;

import java.util.Collections;
import java.util.List;

public abstract class DGPlugin extends JavaPlugin {

    private final String PATH = getClass().getPackageName();

    private final BeanContextId CONTEXT_ID = BeanContextId.of(getName());

    public static Plugin plugin;

	@Override
    public void onLoad() {
		plugin = this;
        initializeBeans();
        onLoaded();
    }

    private void initializeBeans() {
        BeanContext.initializeBeans(CONTEXT_ID, reflectionPaths());
    }

    protected List<String> reflectionPaths() {
	    return Collections.singletonList(PATH);
    }

    @Override
    public void onEnable() {
        initializeListeners();
        initializeCommands();
        onEnabled();
    }

    private void initializeCommands() {
        CommandsContext.registerCommands(this);
    }

    private void initializeListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        List<Listener> listeners = BeanContext.getBeans(CONTEXT_ID, Listener.class);
        listeners.forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    public abstract void onLoaded();

    public abstract void onEnabled();

}
