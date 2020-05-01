package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.loginhistory.LoginHistory;
import pl.subtelny.core.repository.loginhistory.LoginHistoryRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginHistoryService {

    private final AccountService accountService;

    private final LoginHistoryRepository repository;

    private Map<Player, LocalDateTime> loginTimeCache = new ConcurrentHashMap<>();

    @Autowired
    public LoginHistoryService(AccountService accountService, LoginHistoryRepository repository) {
        this.accountService = accountService;
        this.repository = repository;
    }

    public void playerLogin(Player player) {
        loginTimeCache.put(player, LocalDateTime.now());

        accountService.loadAccount(player).whenComplete((account, throwable) -> {
            Optional<LoginHistory> lastLoginHistoryByAccountId = repository.findLastLoginHistoryByAccountId(account.getAccountId());
            System.out.println("lasLogin: " + lastLoginHistoryByAccountId.orElse(null));
        });
    }

    public void playerLogout(Player player) {
        LocalDateTime loginTime = loginTimeCache.get(player);
        if (loginTime != null) {
            loginTimeCache.remove(player);
            accountService.loadAccount(player)
                    .whenComplete((account, throwable) -> {
                        repository.createNewLoginHistory(account.getAccountId(), loginTime);
                    })
                    .handle((account, throwable) -> {
                        throwable.printStackTrace();
                        return throwable;
                    });
        }
    }


}
