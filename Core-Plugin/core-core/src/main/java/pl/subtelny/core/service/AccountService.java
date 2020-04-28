package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.repository.account.AccountRepository;
import pl.subtelny.jobs.JobsProvider;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public CompletableFuture<Account> loadAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        return JobsProvider.supplyAsync(() -> loadAccount(accountId));
    }

    private Account loadAccount(AccountId accountId) {
        Optional<Account> accountOpt = accountRepository.findAccount(accountId);
        return accountOpt.orElseThrow(() -> new NoSuchElementException("Not found account for id " + accountId));
    }

    private void createAccount(Player player, CityType cityType) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        accountRepository.createAccount(accountId, player.getDisplayName(), player.getDisplayName(), cityType);
    }

}
