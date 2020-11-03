package pl.subtelny.utilities.job;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class JobsProvider {

	private final static Executor executors = Executors.newCachedThreadPool();

	public static BukkitTask runSync(Plugin plugin, Runnable runnable) {
		return Bukkit.getScheduler().runTask(plugin, runnable);
	}

	public static BukkitTask runSyncLater(Plugin plugin, Runnable runnable, long time) {
		return Bukkit.getScheduler().runTaskLater(plugin, runnable, time);
	}

	public static <T> CompletableFuture<T> supplyAsync(Job<T> job) {
		return CompletableFuture.supplyAsync(job::execute, executors);
	}

	public static CompletableFuture<Void> runAsync(JobRun job) {
		return CompletableFuture.runAsync(job::execute, executors);
	}

}
