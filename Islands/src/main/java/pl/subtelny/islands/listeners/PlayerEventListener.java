package pl.subtelny.islands.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.service.IslandService;

@Component
public class PlayerEventListener implements Listener {

	private final IslandService islandService;

	@Autowired
	public PlayerEventListener(IslandService islandService) {
		this.islandService = islandService;
	}

	@EventHandler
	public void onPlayerLeashEntity(PlayerLeashEntityEvent e) {
		Player player = e.getPlayer();
		Entity entity = e.getEntity();

		boolean canInteract = islandService.accessToInteract(player, entity);
		if (!canInteract) {
			e.setCancelled(true);
		}
	}

}
