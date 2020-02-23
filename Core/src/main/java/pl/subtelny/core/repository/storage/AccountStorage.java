package pl.subtelny.core.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.repository.Storage;

import java.util.Optional;

public class AccountStorage extends Storage<AccountId, Optional<Account>> {

	public AccountStorage() {
		super(Caffeine.newBuilder().build());
	}

}
