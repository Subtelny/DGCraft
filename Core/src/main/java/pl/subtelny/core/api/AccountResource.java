package pl.subtelny.core.api;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.jobs.JobsProvider;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AccountResource implements Accounts {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountResource(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public FindAccountResult findAccount(AccountId accountId) {
        CompletableFuture<Optional<Account>> future = JobsProvider.supplyAsync(() ->
                accountRepository.findAccount(accountId));
        return new FindAccountResult(future);
    }

    @Override
    public Account getAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        return accountRepository.getAccountIfPresent(accountId)
                .orElseThrow(() -> new NoSuchElementException("Not found account for player " + player.getName()));
    }
}
