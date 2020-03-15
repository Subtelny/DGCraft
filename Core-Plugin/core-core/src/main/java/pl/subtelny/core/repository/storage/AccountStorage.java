package pl.subtelny.core.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.repository.entity.AccountEntity;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.repository.Storage;

import java.util.Optional;

public class AccountStorage extends Storage<AccountId, Optional<AccountEntity>> {

	public AccountStorage() {
		super(Caffeine.newBuilder().build());
	}

}
