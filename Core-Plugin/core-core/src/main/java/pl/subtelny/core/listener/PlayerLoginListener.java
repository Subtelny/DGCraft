package pl.subtelny.core.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.login.PlayerLoginService;
import pl.subtelny.core.player.CorePlayer;
import pl.subtelny.core.player.CorePlayerService;

@Component
public class PlayerLoginListener implements Listener {

    private final PlayerLoginService playerLoginService;

    private final CorePlayerService corePlayerService;

    @Autowired
    public PlayerLoginListener(PlayerLoginService playerLoginService, CorePlayerService corePlayerService) {
        this.playerLoginService = playerLoginService;
        this.corePlayerService = corePlayerService;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        disableFlyingForPlayer(player);
        playerLoginService.loginInPlayer(player);
        respawnPlayer(player);
    }

    public void disableFlyingForPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
    }

    public void respawnPlayer(Player player) {
        CorePlayer corePlayer = corePlayerService.getCorePlayer(player);
        corePlayer.respawn();
    }

}
