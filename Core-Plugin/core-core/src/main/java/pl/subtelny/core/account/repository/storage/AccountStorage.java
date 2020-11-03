package pl.subtelny.core.account.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.repository.Storage;
import pl.subtelny.utilities.NullObject;

public class AccountStorage extends Storage<AccountId, NullObject<Account>> {

	public AccountStorage() {
		super(Caffeine.newBuilder().build());
	}

}
