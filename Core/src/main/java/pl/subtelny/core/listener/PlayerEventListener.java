package pl.subtelny.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.service.AccountService;

@Component
public class PlayerEventListener implements Listener {

    private final AccountService accountService;

    @Autowired
    public PlayerEventListener(AccountService accountService) {
		this.accountService = accountService;
	}

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        accountService.loadPlayer(player);
    }

}
