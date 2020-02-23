package pl.subtelny.core.api;

import org.bukkit.entity.Player;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;

public interface Accounts {

    FindAccountResult findAccount(AccountId accountId);

    Account getAccount(Player player);

}
