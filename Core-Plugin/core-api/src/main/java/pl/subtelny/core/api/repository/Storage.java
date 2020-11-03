package pl.subtelny.core.api.repository;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class Storage<KEY, VALUE> {

	private final Cache<KEY, VALUE> cache;

	public Storage(Cache<KEY, VALUE> cache) {
		this.cache = cache;
	}

	public VALUE getCache(KEY key, Function<? super KEY, ? extends VALUE> function) {
		return cache.get(key, function);
	}

	public Map<KEY, VALUE> getAllCache(List<KEY> keys, Function<Iterable<? extends KEY>, Map<KEY, VALUE>> function) {
		return cache.getAll(keys, function);
	}

	public ConcurrentMap<KEY, VALUE> getAllCache() {
		return cache.asMap();
	}

	public Optional<VALUE> getCacheIfPresent(KEY key) {
		return Optional.ofNullable(cache.getIfPresent(key));
	}

	public void putIfAbsent(KEY key, VALUE value) {
		VALUE present = cache.getIfPresent(key);
		if (present == null) {
			cache.put(key, value);
		}
	}

	public void put(KEY key, VALUE value) {
		cache.put(key, value);
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
