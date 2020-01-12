package pl.subtelny.repository;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class Storage<KEY, VALUE> {

	private Cache<KEY, VALUE> cache;

	public Storage(Cache<KEY, VALUE> cache) {
		this.cache = cache;
	}

	public VALUE getCache(KEY key, Function<? super KEY, ? extends VALUE> function) {
		return cache.get(key, function);
	}

	public Map<KEY, VALUE> getAllCache(List<KEY> keys, Function<Iterable<? extends KEY>, Map<KEY, VALUE>> function) {
		return cache.getAll(keys, function);
	}

	public VALUE getCacheIfPresent(KEY key) {
		return cache.getIfPresent(key);
	}

	public void putIfAbsent(KEY key, VALUE value) {
		VALUE present = cache.getIfPresent(key);
		if (present == null) {
			cache.put(key, value);
		}
	}

	public void updateCache(KEY key, VALUE value) {
		cache.put(key, value);
	}

	public void clear() {
		cache.cleanUp();
	}

	public void invalidate(KEY key) {
		cache.invalidate(key);
	}

}
