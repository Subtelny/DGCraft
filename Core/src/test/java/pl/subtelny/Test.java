package pl.subtelny;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import pl.subtelny.repository.ComputeCache;
import pl.subtelny.repository.Storage;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Test {

    private Storage<UUID, String> storage = new Storage<>(new ComputeCache<UUID, String>() {
        @Override
        public String compute(UUID uuid) {
            System.out.println("computing " + Thread.currentThread().getName());
            String value = "anyValue";
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return value;
        }

        @Override
        public Map<UUID, String> compute(Iterable<? extends UUID> key) {
            System.out.println("computing " + Thread.currentThread().getName());
            String value = "anyValue";
            Map<UUID, String> map = StreamSupport.stream(key.spliterator(), false).collect(Collectors.toMap(o -> o, o -> value));
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return map;
        }
    });

    @org.junit.Test
    public void test() throws InterruptedException {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Set<UUID> uuids = Sets.newHashSet(uuid, uuid2);

        Executor executor = Executors.newFixedThreadPool(2);
        doAsync(executor, "Async1", uuids);
        doAsync(executor, "Async2", uuids);
        TimeUnit.SECONDS.sleep(3);
        doAsync(executor, "Async3", uuids);

        TimeUnit.SECONDS.sleep(10);
    }

    private void doAsync(Executor executor, String name, Set<UUID> uuids) {
        CompletableFuture.runAsync(() -> {
            String threadName = Thread.currentThread().getName();
            long time = System.currentTimeMillis();

            try {
                ImmutableMap<UUID, String> map = storage.getCache(uuids);
                System.out.println(name + " - " + threadName + " | values " + Arrays.toString(map.entrySet().toArray()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis() - time;
            System.out.println(name + " - " + threadName + " | Returned cache in " + (endTime));
        }, executor);
    }

}
