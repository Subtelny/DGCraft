package pl.subtelny.core.api.account;

import org.bukkit.entity.Player;

public interface Accounts {

    Account getAccount(Player player);

    Account getAccount(AccountId accountId);

}
