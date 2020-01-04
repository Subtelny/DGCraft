package pl.subtelny.islands.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.service.IslandActionGuard;
import pl.subtelny.islands.service.IslandService;

@Component
public class PlayerEventListener implements Listener {

	private final IslandActionGuard islandActionGuard;

	@Autowired
	public PlayerEventListener(IslandActionGuard islandActionGuard) {
		this.islandActionGuard = islandActionGuard;
	}

	public void onPlayerJoin(PlayerJoinEvent e) {

	}

	@EventHandler
	public void onPlayerLeashEntity(PlayerLeashEntityEvent e) {
		Player player = e.getPlayer();
		Entity entity = e.getEntity();

		boolean canInteract = islandActionGuard.accessToInteract(player, entity);
		if (!canInteract) {
			e.setCancelled(true);
		}
	}

}
