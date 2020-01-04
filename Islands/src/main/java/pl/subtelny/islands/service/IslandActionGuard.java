package pl.subtelny.islands.service;

import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;

@Component
public class IslandActionGuard {

	private final IslandService islandService;

	public IslandActionGuard(IslandService islandService) {
		this.islandService = islandService;
	}

	public boolean accessToInteract(Entity entity, Entity toIncteract) {
		if (entity.hasPermission("dgcraft.islands.interact.bypass")) {
			return true;
		}
		Optional<Island> islandAtLocationOpt = islandService.findIslandAtLocation(toIncteract.getLocation());
		if (islandAtLocationOpt.isPresent()) {
			Island islandAtLocation = islandAtLocationOpt.get();
			return islandAtLocation.canInteract(entity, toIncteract);
		}
		return true;
	}

	public boolean accessToHit(Entity attacker, Entity victim) {
		if (attacker.hasPermission("dgcraft.islands.attack.bypass")) {
			return true;
		}
		Optional<Island> victimsIslandOpt = islandService.findIslandAtLocation(victim.getLocation());
		if (victimsIslandOpt.isPresent()) {
			Island victimsIsland = victimsIslandOpt.get();
			return victimsIsland.canHit(attacker, victim);
		}
		return true;
	}

	public boolean accessToBuild(Player player, Location location) {
		if (player.hasPermission("dgcraft.islands.build.bypass")) {
			return true;
		}
		Optional<Island> islandAtLocationOpt = islandService.findIslandAtLocation(location);
		if (islandAtLocationOpt.isPresent()) {
			Island islandAtLocation = islandAtLocationOpt.get();
			return islandAtLocation.canBuild(player);
		}
		return true;
	}

}
