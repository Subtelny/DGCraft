package pl.subtelny.jobs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.bukkit.Bukkit;

public final class JobsProvider {

	private final static Executor executors = Executors.newCachedThreadPool();

	public static Executor getExecutor() {
		return executors;
	}

	public static <T> CompletableFuture<T> supplyAsync(JobApply<T> job) {
		return CompletableFuture.supplyAsync(job::execute, executors);
	}

	public static void async(Job job) {
		//Bukkit.getScheduler().runTaskAsynchronously(DGPlugin.plugin, job::execute);
	}

	public static void sync(Job job) {
		//Bukkit.getScheduler().runTask(DGPlugin.plugin, job::execute);
	}

}
