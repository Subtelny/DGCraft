package pl.subtelny.jobs;

import org.bukkit.Bukkit;
import pl.subtelny.plugin.DGPlugin;

public final class JobsProvider {

	public static void async(Job job) {
		Bukkit.getScheduler().runTaskAsynchronously(DGPlugin.plugin, job::execute);
	}

	public static void sync(Job job) {
		Bukkit.getScheduler().runTask(DGPlugin.plugin, job::execute);
	}

}
