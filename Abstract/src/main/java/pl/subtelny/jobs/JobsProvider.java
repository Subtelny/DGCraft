package pl.subtelny.jobs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.bukkit.Bukkit;
import pl.subtelny.plugin.DGPlugin;

public final class JobsProvider {

	private final static Executor executors = Executors.newCachedThreadPool();

	public static Executor getExecutors() {
		return executors;
	}

	public static void async(Job job) {
		Bukkit.getScheduler().runTaskAsynchronously(DGPlugin.plugin, job::execute);
	}

	public static void sync(Job job) {
		Bukkit.getScheduler().runTask(DGPlugin.plugin, job::execute);
	}

}
