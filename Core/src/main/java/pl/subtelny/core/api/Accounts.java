package pl.subtelny.core.api;

import org.bukkit.entity.Player;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;

import java.util.Optional;

public interface Accounts {

    Account getAccount(Player player);

    Optional<Account> findAccount(AccountId accountId);

}
