package pl.subtelny.core.login;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Accounts;

@Component
public class PlayerLoginService {

    private final Accounts accounts;

    @Autowired
    public PlayerLoginService(Accounts accounts) {
        this.accounts = accounts;
    }

    public void loginInPlayer(Player player) {
        if (accounts.findAccount(player).isEmpty()) {
            accounts.createAccount(player);
        }
    }

}
