package pl.subtelny.islands.api.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.repository.Storage;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.utilities.NullObject;

public class IslandStorage<T extends Island> extends Storage<IslandId, NullObject<T>> {

    public IslandStorage() {
        super(Caffeine.newBuilder().build());
    }

}
