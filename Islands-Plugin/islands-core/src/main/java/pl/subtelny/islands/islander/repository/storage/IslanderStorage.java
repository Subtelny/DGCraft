package pl.subtelny.islands.islander.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;

import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.repository.Storage;

public class IslanderStorage extends Storage<IslanderId, Optional<Islander>> {

	public IslanderStorage() {
		super(Caffeine.newBuilder().build());
	}

}
