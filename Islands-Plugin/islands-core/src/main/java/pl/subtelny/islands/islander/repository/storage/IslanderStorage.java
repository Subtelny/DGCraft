package pl.subtelny.islands.islander.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.repository.LoadingStorage;
import pl.subtelny.islands.api.IslanderId;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.NullObject;

public class IslanderStorage extends LoadingStorage<IslanderId, NullObject<Islander>> {

    public IslanderStorage(IslanderCacheLoader islanderCacheLoader) {
        super(Caffeine.newBuilder()
                .build(islanderCacheLoader));
    }
}
