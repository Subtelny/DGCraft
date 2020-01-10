package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.core.repository.loader.AccountAnemia;
import pl.subtelny.core.repository.storage.AccountStorage;
import pl.subtelny.jobs.JobsProvider;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AccountService implements Accounts {

    private final AccountStorage accountStorage;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountStorage accountStorage,
                          AccountRepository accountRepository) {
        this.accountStorage = accountStorage;
        this.accountRepository = accountRepository;
    }

    public void loadPlayer(Player player) {
        CompletableFuture.runAsync(() -> getAccount(player), JobsProvider.getExecutor());
    }

    @Override
    public Account getAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        Optional<Account> accountOpt = accountStorage.getCache(accountId);
        if (accountOpt.isEmpty()) {
            return createNewAccount(player);
        }
        return accountOpt.get();
    }

    @Override
    public Optional<Account> findAccount(AccountId accountId) {
        return accountStorage.getCache(accountId);
    }

    private Account createNewAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        AccountAnemia accountAnemia = new AccountAnemia(accountId);
        Account account = new Account(accountAnemia);
        account.setDisplayName(player.getDisplayName());
        account.setLastOnline(LocalDateTime.now());
        account.setName(player.getName());
        saveAccount(account);
        return account;
    }

	private void saveAccount(Account account) {
        accountStorage.updateCache(account.getAccountId(), Optional.of(account));
		JobsProvider.async(() -> {
            AccountAnemia accountAnemia = account.getAccountAnemia();
            accountRepository.saveAccount(accountAnemia);
        });
	}

}
