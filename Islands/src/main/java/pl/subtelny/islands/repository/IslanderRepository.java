package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.repository.Storage;

@Component
public class IslanderRepository extends Storage<AccountId, Optional<Islander>> {

	private final AccountRepository accountRepository;

	@Autowired
	public IslanderRepository(AccountRepository accountRepository) {
		super(getStorageCacheBuild());
		this.accountRepository = accountRepository;
	}

	@NonNull
	private static Cache<AccountId, Optional<Islander>> getStorageCacheBuild() {
		return Caffeine.newBuilder().build();
	}

	private Optional<Islander> loadIslander(AccountId accountId) {
		Optional<Account> account = accountRepository.findAccount(accountId);
		account.orElseThrow(() -> new NoSuchElementException(String.format("Not found Account for id %s", accountId)));

		Islander islander = new Islander(account.get());
		return Optional.of(islander);
	}

	@Override
	public Function<? super AccountId, ? extends Optional<Islander>> computeData() {
		return this::loadIslander;
	}

}
