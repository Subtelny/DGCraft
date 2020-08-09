package pl.subtelny.core.login;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;

@Component
public class PlayerLoginService {

    private final Accounts accounts;

    @Autowired
    public PlayerLoginService(Accounts accounts) {
        this.accounts = accounts;
    }

    public Account loginInPlayer(Player player) {
        return accounts.findAccount(player).orElse(accounts.createAccount(player));
    }

}
