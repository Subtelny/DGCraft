package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.core.repository.entity.AccountEntity;
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
        AccountEntity account = new AccountEntity(
                accountId,
                player.getName(),
                player.getDisplayName(),
                LocalDateTime.now()
        );
        accountRepository.saveAccount(account);
    }

}
