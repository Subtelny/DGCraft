package pl.subtelny.core;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.ComponentContext;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.core.listener.ServerInitializeListener;

import java.util.Arrays;

public class Core extends ComponentPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        disallowPlayerLogin();
        ComponentContext.initialize(this);
    }

    @Override
    public void onInitialize(ComponentProvider componentProvider) {
        allowPlayerLogin();
    }

    private void disallowPlayerLogin() {
        Bukkit.getPluginManager().registerEvents(new ServerInitializeListener(), this);
    }

    private void allowPlayerLogin() {
        Arrays.stream(PlayerLoginEvent.getHandlerList().getRegisteredListeners())
                .filter(registeredListener -> registeredListener.getListener() instanceof ServerInitializeListener)
                .findFirst()
                .ifPresent(registeredListener -> PlayerLoginEvent.getHandlerList().unregister(registeredListener));
    }
}
