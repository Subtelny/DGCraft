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

    private final AccountService accountService;

    @Autowired
    public PlayerJoinListener(AccountService accountService) {
		this.accountService = accountService;
	}

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        accountService.syncAccountWithPlayer(player);
    }

}
