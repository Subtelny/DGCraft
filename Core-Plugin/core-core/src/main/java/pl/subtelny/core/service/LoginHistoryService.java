package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.repository.loginhistory.LoginHistoryRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginHistoryService {

    private final Accounts accounts;

    private final LoginHistoryRepository repository;

    private Map<Player, LocalDateTime> loginTimeCache = new ConcurrentHashMap<>();

    @Autowired
    public LoginHistoryService(Accounts accounts, LoginHistoryRepository repository) {
        this.accounts = accounts;
        this.repository = repository;
    }

    public void playerLogin(Player player) {
        loginTimeCache.put(player, LocalDateTime.now());
    }

    public void playerLogout(Player player) {
        LocalDateTime loginTime = loginTimeCache.get(player);
        if (loginTime != null) {
            loginTimeCache.remove(player);
            accounts.findAccountAsync(player)
                    .whenComplete((accountOpt, throwable) ->
                            accountOpt.ifPresent(account -> repository
                                    .createNewLoginHistory(account.getAccountId(), loginTime)));
        }
    }


}
