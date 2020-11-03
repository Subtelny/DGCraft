package pl.subtelny.islands.island.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.repository.Storage;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.utilities.NullObject;

public class IslandStorage<T extends Island> extends Storage<IslandId, NullObject<T>> {

    public IslandStorage() {
        super(Caffeine.newBuilder().build());
    }

}
