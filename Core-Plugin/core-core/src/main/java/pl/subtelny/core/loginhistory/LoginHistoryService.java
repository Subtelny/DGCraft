package pl.subtelny.core.loginhistory;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.loginhistory.repository.LoginHistoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginHistoryService {

    private static final int MIN_MINUTES_TO_LOG_TIME = 2;

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
            LocalDateTime now = LocalDateTime.now();
            if (Duration.between(loginTime, now).toMinutes() < MIN_MINUTES_TO_LOG_TIME) {
                return;
            }
            loginTimeCache.remove(player);
            saveLoginHistory(player, loginTime);
        }
    }

    private void saveLoginHistory(Player player, LocalDateTime loginTime) {
        accounts.findAccount(player)
                .ifPresent(account -> repository.createNewLoginHistory(account.getAccountId(), loginTime));
    }

}
