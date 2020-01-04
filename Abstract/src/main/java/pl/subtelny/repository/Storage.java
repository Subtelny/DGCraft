package pl.subtelny.repository;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class Storage<KEY, VALUE> {

	private Cache<KEY, VALUE> cache;

	public Storage(Cache<KEY, VALUE> cache) {
		this.cache = cache;
	}

	public VALUE getCache(KEY key) {
		return cache.get(key, computeData());
	}

	public Map<KEY, VALUE> getCache(List<KEY> keys) {
		return cache.getAll(keys, computeDataIterable());
	}

	protected Function<Iterable<? extends KEY>, Map<KEY, VALUE>> computeDataIterable() {
		return keys -> StreamSupport
				.stream(keys.spliterator(), false)
				.collect(Collectors.toMap(o -> o, this::getCache));
	}

	public void clear() {
		cache.cleanUp();
	}

	public void invalidate(KEY key) {
		cache.invalidate(key);
	}

	protected abstract Function<? super KEY, ? extends VALUE> computeData();

}
