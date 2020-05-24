package pl.subtelny.core.login;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.Core;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.PlayerUtil;
import pl.subtelny.utilities.log.LogUtil;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class PlayerLoginService {

    private final Accounts accounts;

    private final CoreMessages messages;

    private Set<Player> loggingInPlayers = Sets.newConcurrentHashSet();

    @Autowired
    public PlayerLoginService(Accounts accounts, CoreMessages messages) {
        this.accounts = accounts;
        this.messages = messages;
    }

    public CompletableFuture<Optional<Account>> loginInPlayer(Player player) {
        loggingInPlayers.add(player);
        return accounts.findAccountAsync(player);
    }

    public boolean isPlayerLoggingIn(Player player) {
        return loggingInPlayers.contains(player);
    }

    private void loginInCompleted(Player player) {
        loggingInPlayers.remove(player);
    }

    private void accountLoadedExceptionally(Player player, Throwable throwable) {
        if (player != null) {
            LogUtil.warning(String.format("Player %s kicked of data load failed reason: %s ", player.getName(), throwable.getMessage()));
            JobsProvider.runSync(Core.plugin, () -> {
                String message = messages.get("account_data_loaded_exceptionally");
                PlayerUtil.kick(player, message);
            });
        }
    }

}
