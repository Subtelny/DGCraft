package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountAnemia;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.jobs.JobsProvider;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void loadPlayer(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        JobsProvider.async(() -> loadOrCreateAccountIfNotExist(player, accountId));
    }

    private void loadOrCreateAccountIfNotExist(Player player, AccountId accountId) {
        Optional<Account> accountOpt = accountRepository.findAccount(accountId);
        if (accountOpt.isEmpty()) {
            createNewAccount(player);
        }
    }

    private void createNewAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        AccountAnemia accountAnemia = new AccountAnemia(accountId);
        Account account = new Account(accountAnemia);
        account.setDisplayName(player.getDisplayName());
        account.setLastOnline(LocalDateTime.now());
        account.setName(player.getName());
        accountRepository.saveAccount(account);
    }

}
