package pl.subtelny.core.repository.storage;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountAnemia;
import pl.subtelny.core.repository.loader.AccountLoadRequest;
import pl.subtelny.repository.Storage;

public class AccountStorage extends Storage<AccountId, Optional<Account>> {

	private Cache<AccountLoadRequest, Optional<AccountId>> requestCache;

	public AccountStorage() {
		super(Caffeine.newBuilder().build());
		requestCache = Caffeine.newBuilder().build();
	}

	public Optional<AccountId> getCache(AccountLoadRequest request,
			Function<? super AccountLoadRequest, ? extends Optional<AccountId>> requestFunction) {
		return requestCache.get(request, requestFunction);
	}

	public void invalidateRequests(AccountAnemia accountAnemia) {
		ConcurrentMap<AccountLoadRequest, Optional<AccountId>> requestsAsMap = requestCache.asMap();
		List<AccountLoadRequest> requests = requestsAsMap.keySet()
				.stream()
				.filter(accountId -> hasReferenceToAccountAnemia(accountId, accountAnemia))
				.collect(Collectors.toList());
		requestCache.invalidateAll(requests);
	}

	private boolean hasReferenceToAccountAnemia(AccountLoadRequest request, AccountAnemia accountAnemia) {
		Optional<AccountId> accountIdOpt = request.getAccountId();
		if (accountIdOpt.isPresent()) {
			AccountId accountId = accountIdOpt.get();
			boolean hasSameId = accountAnemia.getAccountId().equals(accountId);
			if (hasSameId) {
				return true;
			}
		}
		Optional<String> nameOpt = request.getName();
		if (nameOpt.isPresent()) {
			String name = nameOpt.get();
			return accountAnemia.getName().equals(name);
		}
		return false;
	}

}
