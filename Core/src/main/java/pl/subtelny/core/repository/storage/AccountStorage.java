package pl.subtelny.core.repository.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.function.Function;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.loader.AccountAnemiaLoadRequest;
import pl.subtelny.repository.Storage;

public class AccountStorage extends Storage<AccountId, Optional<Account>> {

	private Cache<AccountAnemiaLoadRequest, Optional<AccountId>> requestCache;

	public AccountStorage() {
		super(Caffeine.newBuilder().build());
		requestCache = Caffeine.newBuilder().build();
	}

	public Optional<AccountId> getCache(AccountAnemiaLoadRequest request,
			Function<? super AccountAnemiaLoadRequest, ? extends Optional<AccountId>> requestFunction) {
		return requestCache.get(request, requestFunction);
	}

}
