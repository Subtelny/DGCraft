package pl.subtelny.core.service;

import pl.subtelny.components.api.Autowired;
import pl.subtelny.components.api.Component;
import pl.subtelny.core.model.AccountEntity;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import org.bukkit.entity.Player;
import pl.subtelny.core.repository.AccountAnemia;
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
        Optional<AccountEntity> accountOpt = accountRepository.findAccount(accountId);
        if (accountOpt.isEmpty()) {
            createNewAccount(player);
        }
    }

    private void createNewAccount(Player player) {
        AccountId accountId = AccountId.of(player.getUniqueId());
        AccountAnemia accountAnemia = new AccountAnemia(accountId);
        AccountEntity account = new AccountEntity(accountAnemia);
        account.setDisplayName(player.getDisplayName());
        account.setLastOnline(LocalDateTime.now());
        account.setName(player.getName());
        accountRepository.saveAccount(account);
    }

}
