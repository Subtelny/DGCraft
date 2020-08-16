package pl.subtelny.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.player.CorePlayerService;

@Component
public class PlayerLogoutListener implements Listener {

    private final CorePlayerService corePlayerService;

    @Autowired
    public PlayerLogoutListener(CorePlayerService corePlayerService) {
        this.corePlayerService = corePlayerService;
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent e) {
        corePlayerService.invalidatePlayer(e.getPlayer());
    }

}
