package pl.subtelny.core.repository.account.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.repository.Storage;
import pl.subtelny.utilities.NullObject;

import java.util.Optional;

public class AccountStorage extends Storage<AccountId, NullObject<Account>> {

	public AccountStorage() {
		super(Caffeine.newBuilder().build());
	}

}
