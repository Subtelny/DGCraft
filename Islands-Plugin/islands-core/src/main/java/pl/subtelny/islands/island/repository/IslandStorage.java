package pl.subtelny.islands.island.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.repository.Storage;
import pl.subtelny.utilities.NullObject;

public class IslandStorage extends Storage<IslandId, NullObject<Island>> {

    public IslandStorage() {
        super(Caffeine.newBuilder().build());
    }

}
