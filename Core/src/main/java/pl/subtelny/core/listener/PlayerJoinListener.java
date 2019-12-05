package pl.subtelny.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.service.AccountService;

@Component
public class PlayerJoinListener implements Listener {

    private final AccountService userService;

    @Autowired
    public PlayerJoinListener(AccountService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        userService.createUserIfNotExists(player);
    }

}
