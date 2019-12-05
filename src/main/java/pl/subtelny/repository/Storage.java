package pl.subtelny.repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Storage<KEY, VALUE> {

    private final ComputeCache<KEY, VALUE> computeCache;

    private LoadingCache<KEY, VALUE> cache;

    public Storage(ComputeCache<KEY, VALUE> computeCache) {
        this.computeCache = computeCache;
        this.cache = initializeCache();
    }

    private LoadingCache<KEY, VALUE> initializeCache() {
        return CacheBuilder.newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(new CacheLoader<KEY, VALUE>() {
                    @Override
                    public VALUE load(KEY key) {
                        return computeCache.compute(key);
                    }
                    @Override
                    public Map<KEY, VALUE> loadAll(Iterable<? extends KEY> keys) {
                        return computeCache.compute(keys);
                    }
                });
    }

    public VALUE getCache(KEY key) {
        return cache.getUnchecked(key);
    }

    public ImmutableMap<KEY, VALUE> getCache(Set<KEY> keys) throws ExecutionException {
        return cache.getAll(keys);
    }

    public void clear() {
        cache.invalidateAll();
    }

    public void invalidate(KEY key) {
        cache.invalidate(key);
    }

    public void put(KEY key, VALUE value) {
        cache.put(key, value);
    }

}
