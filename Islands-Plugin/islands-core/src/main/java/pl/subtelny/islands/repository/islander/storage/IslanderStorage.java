package pl.subtelny.islands.repository.islander.storage;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;

import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.islands.model.islander.IslanderId;
import pl.subtelny.repository.Storage;

public class IslanderStorage extends Storage<IslanderId, Optional<Islander>> {

	public IslanderStorage() {
		super(Caffeine.newBuilder().build());
	}

}
