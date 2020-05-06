package pl.subtelny.core.service.account;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;

import java.util.Optional;

@Component
public class PlayerService {

    private final AccountService accountService;

    @Autowired
    public PlayerService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void respawnPlayer(Player player) {
        Optional<Account> accountOpt = accountService.findAccount(player);
        if (accountOpt.isPresent()) {

        }
    }

}
