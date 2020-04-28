package pl.subtelny.jobs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class JobsProvider {

	private final static Executor executors = Executors.newCachedThreadPool();

	public static <T> CompletableFuture<T> supplyAsync(JobApply<T> job) {
		return CompletableFuture.supplyAsync(job::execute, executors);
	}

	public static CompletableFuture<Void> runAsync(JobRun job) {
		return CompletableFuture.runAsync(job::execute, executors);
	}

}
