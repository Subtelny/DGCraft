package pl.subtelny.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.loginhistory.LoginHistoryService;

@Component
public class PlayerLoginHistoryListener implements Listener {

    private final LoginHistoryService loginHistoryService;

    @Autowired
    public PlayerLoginHistoryListener(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        loginHistoryService.playerLogin(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        loginHistoryService.playerLogout(e.getPlayer());
    }

}
