package pl.subtelny.islands.repository.islander.storage;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;

import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.repository.Storage;

public class IslanderStorage extends Storage<AccountId, Optional<Islander>> {

	public IslanderStorage() {
		super(Caffeine.newBuilder().build());
	}

}
