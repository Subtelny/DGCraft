package pl.subtelny.core.login.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.login.PlayerLoginService;
import pl.subtelny.core.service.PlayerService;
import pl.subtelny.utilities.location.LocationUtil;

@Component
public class PlayerLoginListener implements Listener {

    private final PlayerService playerService;

    private final PlayerLoginService playerLoginService;

    private final CoreMessages messages;

    @Autowired
    public PlayerLoginListener(PlayerService playerService, PlayerLoginService playerLoginService, CoreMessages messages) {
        this.playerService = playerService;
        this.playerLoginService = playerLoginService;
        this.messages = messages;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        playerLoginService.loginInPlayer(player)
                .whenComplete((accountOpt, throwable) ->
                        accountOpt.ifPresentOrElse(
                                account -> playerLoggedIn(player, account),
                                () -> playerNotHaveAccount(player)
                        )
                );
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to != null && LocationUtil.isSameLocationPrecisionToBlock(from, to)) {
            return;
        }
        Player player = e.getPlayer();
        if (playerLoginService.isPlayerLoggingIn(player)) {
            e.setCancelled(true);
            messages.sendTo(player, "player.login.loading");
        }
    }

    private void playerLoggedIn(Player player, Account account) {
        player.setGameMode(GameMode.SURVIVAL);
        playerService.teleportToCitySpawn(player, account);
    }

    private void playerNotHaveAccount(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        playerService.teleportToGlobalSpawn(player);
    }

}
