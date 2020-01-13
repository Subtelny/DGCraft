package pl.subtelny;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.islands.repository.island.synchronizer.Synchronizer;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Test extends Synchronizer<Integer> {

    @org.junit.Test
    public void test() {
        Executor executor = Executors.newCachedThreadPool();

        Cache<Integer, String> cache = Caffeine.newBuilder().build();
        Integer key = 1;

        CompletableFuture.runAsync(() -> {
            System.out.println("START THREAD #1 = " + Thread.currentThread().getName());
            String s = cache.get(key, integer -> computeCache(integer));
            System.out.println(Thread.currentThread().getName() + " - " + s);
        }, executor);

        CompletableFuture.runAsync(() -> {
            System.out.println("START THREAD #2 = " + Thread.currentThread().getName());
            //cache.put(key, key + "-heh");
            String s = cache.get(1, this::computeCache);
            System.out.println(Thread.currentThread().getName() + " - " + s);
        }, executor);
        CompletableFuture.runAsync(() -> {
            System.out.println("START THREAD #3 = " + Thread.currentThread().getName());
            String s = cache.get(key, this::computeCache);
            System.out.println(Thread.currentThread().getName() + " - " + s);
        }, executor);

        try {
            SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String computeCache(Integer key) {
        try {
            SECONDS.sleep(2);
            return key + "-value";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
