package pl.subtelny.islands.island.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.repository.Storage;

import java.util.Optional;

@Component
public class IslandStorage extends Storage<IslandId, Optional<Island>> {

	public IslandStorage() {
		super(Caffeine.newBuilder().build());
	}

}
