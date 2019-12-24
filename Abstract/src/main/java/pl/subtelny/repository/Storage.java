package pl.subtelny.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Storage<KEY, VALUE> {

    private Cache<KEY, VALUE> cache;

    public Storage() {
        this.cache = initializeCache();
    }

    private Cache<KEY, VALUE> initializeCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
    }

    public VALUE getCache(KEY key, Function<? super KEY, ? extends VALUE> mappingFunction) {
        return cache.get(key, mappingFunction);
    }

    public Map<KEY, VALUE> getCache(List<KEY> keys, Function<Iterable<? extends KEY>, Map<KEY, VALUE>> mappingFunction) {
        return cache.getAll(keys, mappingFunction);
    }

    public void clear() {
        cache.cleanUp();
    }

    public void invalidate(KEY key) {
        cache.invalidate(key);
    }

}
