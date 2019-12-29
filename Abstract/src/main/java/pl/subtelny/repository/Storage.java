package pl.subtelny.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class Storage<KEY, VALUE> {

    private Cache<KEY, VALUE> cache;

    public Storage(Cache<KEY, VALUE> cache) {
        this.cache = cache;
    }

    public VALUE getCache(KEY key) {
        return cache.get(key, mappingFunction());
    }

    /*public Map<KEY, VALUE> getCache(List<KEY> keys) {
        return cache.getAll(keys, mappingFunctionIterable());
    }*/

    public void clear() {
        cache.cleanUp();
    }

    public void invalidate(KEY key) {
        cache.invalidate(key);
    }

    public abstract Function<? super KEY, ? extends VALUE> mappingFunction();

	//public abstract Function<Iterable<? extends KEY>, Map<KEY, VALUE>> mappingFunctionIterable();

}
