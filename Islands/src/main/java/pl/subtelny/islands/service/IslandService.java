package pl.subtelny.islands.service;

import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.SkyblockIslandRepository;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.SkyblockIslandUtil;

@Component
public class IslandService {

	private final SkyblockIslandRepository skyblockIslandRepository;

	@Autowired
	public IslandService(SkyblockIslandRepository skyblockIslandRepository) {
		this.skyblockIslandRepository = skyblockIslandRepository;
	}

	public Optional<Island> findIslandByPlayer(Player player) {


		return Optional.empty();
	}

	public Optional<Island> findIslandAtLocation(Location location) {
		if (Settings.SkyblockIsland.ISLAND_WORLD.equals(location.getWorld())) {
			IslandCoordinates islandCoordinates = SkyblockIslandUtil.getIslandCoordinates(location);
			Optional<SkyblockIsland> island = skyblockIslandRepository.findIsland(islandCoordinates);
			return Optional.ofNullable(island.orElse(null));
		}
		//TODO
		//guilds
		return Optional.empty();
	}

	public boolean accessToInteract(Entity entity, Entity toIncteract) {
		if (entity.hasPermission("dgcraft.islands.interact.bypass")) {
			return true;
		}
		Optional<Island> islandAtLocationOpt = findIslandAtLocation(toIncteract.getLocation());
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
		Optional<Island> victimsIslandOpt = findIslandAtLocation(victim.getLocation());
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
		Optional<Island> islandAtLocationOpt = findIslandAtLocation(location);
		if (islandAtLocationOpt.isPresent()) {
			Island islandAtLocation = islandAtLocationOpt.get();
			return islandAtLocation.canBuild(player);
		}
		return true;
	}

}
