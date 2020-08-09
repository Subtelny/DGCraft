package pl.subtelny.core.login.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.login.PlayerLoginService;

@Component
public class PlayerLoginListener implements Listener {

    private final PlayerLoginService playerLoginService;

    @Autowired
    public PlayerLoginListener(PlayerLoginService playerLoginService) {
        this.playerLoginService = playerLoginService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        playerLoginService.loginInPlayer(player);
    }

}
