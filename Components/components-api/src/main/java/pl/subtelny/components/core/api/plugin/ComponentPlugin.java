package pl.subtelny.components.core.api.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import pl.subtelny.components.core.api.ComponentProvider;

public abstract class ComponentPlugin extends JavaPlugin {

    public void onInitialize(ComponentProvider componentProvider) {
        //Noop by default
    }

}
