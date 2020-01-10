package pl.subtelny.core.repository.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.core.repository.loader.AccountAnemia;
import pl.subtelny.repository.Storage;

import java.util.Optional;
import java.util.function.Function;

@Component
public class AccountStorage extends Storage<AccountId, Optional<Account>> {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountStorage(AccountRepository accountRepository) {
        super(Caffeine.newBuilder().build());
        this.accountRepository = accountRepository;
    }

    @Override
    protected Function<? super AccountId, ? extends Optional<Account>> computeData() {
        return accountId -> {
            Optional<AccountAnemia> accountOpt = accountRepository.findAccount(accountId);
            return accountOpt.map(AccountMapper::map);
        };
    }

    private static class AccountMapper {

        private static Account map(AccountAnemia accountAnemia) {
            return new Account(accountAnemia);
        }

    }

}
