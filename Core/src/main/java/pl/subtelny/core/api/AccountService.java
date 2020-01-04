package pl.subtelny.core.api;

import org.bukkit.entity.Player;
import pl.subtelny.core.model.Account;

public interface AccountService {

	Account getAccount(Player player);

	void saveAccount(Account account);

}
