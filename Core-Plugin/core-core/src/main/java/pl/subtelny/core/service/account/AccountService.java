package pl.subtelny.core.service.account;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.*;
import pl.subtelny.core.repository.account.AccountRepository;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AccountService implements Accounts {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccount(AccountId accountId) {
        return accountRepository.findAccount(accountId);
    }

    @Override
    public CompletableFuture<Optional<Account>> findAccountAsync(AccountId accountId) {
        return JobsProvider.supplyAsync(() -> findAccount(accountId));
    }

    @Override
    public void createAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        String name = player.getName();
        String displayName = player.getDisplayName();
        CreateAccountRequest request = CreateAccountRequest.of(accountId, name, displayName);
        accountRepository.createAccount(request);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.saveAccount(account);
    }
}
