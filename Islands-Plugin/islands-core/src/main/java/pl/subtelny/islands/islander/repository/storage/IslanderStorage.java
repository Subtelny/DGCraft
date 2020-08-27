package pl.subtelny.islands.islander.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;

import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.repository.Storage;
import pl.subtelny.utilities.NullObject;

public class IslanderStorage extends Storage<IslanderId, NullObject<Islander>> {

	public IslanderStorage() {
		super(Caffeine.newBuilder().build());
	}

}
