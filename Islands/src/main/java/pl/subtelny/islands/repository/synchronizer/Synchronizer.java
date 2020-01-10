package pl.subtelny.islands.repository.synchronizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Synchronizer<KEY> {

    public final Map<KEY, ReentrantLock> sync = new ConcurrentHashMap<>();

    protected void lock(KEY key) {
        synchronized (sync.computeIfAbsent(key, key1 -> new ReentrantLock())) {
            ReentrantLock lock = sync.get(key);
            lock.lock();
        }
    }

    protected void unlock(KEY key) {
        ReentrantLock reentrantLock = sync.get(key);
        reentrantLock.unlock();
        if (reentrantLock.getQueueLength() == 0) {
            sync.remove(key);
        }
    }

}
