package pl.subtelny.repository;

import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Updater<ANEMIA> {

	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	private final Queue<ANEMIA> updateQueue = Queues.newLinkedBlockingQueue();

	public void addToQueue(ANEMIA anemia) {
		updateQueue.offer(anemia);
		performUpdater();
	}

	private void performUpdater() {
		if (!executorService.isTerminated()) {
			return;
		}
		executorService.submit(() -> {
			Iterator<ANEMIA> iterator = updateQueue.iterator();
			while (iterator.hasNext()) {
				ANEMIA next = iterator.next();
				performAction(next);
				iterator.remove();
			}
		});
	}

	protected abstract void performAction(ANEMIA anemia);


}
