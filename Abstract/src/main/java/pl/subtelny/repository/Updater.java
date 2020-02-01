package pl.subtelny.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Updater<ANEMIA> {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final LinkedBlockingQueue<ANEMIA> updateQueue = Queues.newLinkedBlockingQueue();

    public void addToQueue(ANEMIA anemia) {
        updateQueue.offer(anemia);
        performUpdater();
    }

    private void performUpdater() {
        if (!executorService.isTerminated()) {
            return;
        }
        ArrayList<ANEMIA> list = Lists.newArrayList();
        updateQueue.drainTo(list);
        executorService.submit(() -> list.forEach(this::performAction));
    }

    protected abstract void performAction(ANEMIA anemia);


}
