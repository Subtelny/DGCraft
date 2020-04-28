package pl.subtelny.utilities.thread;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class ThreadUtil {

    public static void runSync(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

}
