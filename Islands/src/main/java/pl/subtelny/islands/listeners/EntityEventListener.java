package pl.subtelny.islands.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTameEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.service.IslandService;

@Component
public class EntityEventListener implements Listener {

	private final IslandService islandService;

	@Autowired
	public EntityEventListener(IslandService islandService) {
		this.islandService = islandService;
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Location from = e.getLocation();
		List<Block> blocks = e.blockList();

		Optional<Island> islandFromOpt = islandService.findIslandAtLocation(from);
		Iterator<Block> iterator = blocks.iterator();
		while (iterator.hasNext()) {
			Block next = iterator.next();
			Location location = next.getLocation();
			if (!preventExplode(islandFromOpt, location)) {
				iterator.remove();
			}
		}
	}

	private boolean preventExplode(Optional<Island> islandFromOpt, Location match) {
		Optional<Island> islandMatchOpt = islandService.findIslandAtLocation(match);

		if (islandFromOpt.isEmpty() && islandMatchOpt.isPresent()) {
			return true;
		}
		if (islandFromOpt.isPresent() && islandMatchOpt.isEmpty()) {
			return true;
		}
		if (islandFromOpt.isPresent()) {
			Island islandFrom = islandFromOpt.get();
			Island islandMatch = islandMatchOpt.get();
			return !islandFrom.equals(islandMatch);
		}
		return false;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Entity entity = e.getEntity();
		Entity attacker = e.getDamager();
		boolean canHit = islandService.accessToHit(attacker, entity);
		if (!canHit) {
			e.setDamage(0);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityTame(EntityTameEvent e) {
		AnimalTamer owner = e.getOwner();
		if (e.isCancelled() || !(owner instanceof Player)) {
			return;
		}
		Player player = (Player) owner;
		LivingEntity entity = e.getEntity();

		boolean canInteract = islandService.accessToInteract(player, entity);
		if (!canInteract) {
			e.setCancelled(true);
		}
	}

}
