package pl.subtelny.islands.service;

import java.util.Optional;
import org.bukkit.Location;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.repository.island.storage.SkyblockIslandStorage;
import pl.subtelny.islands.utils.SkyblockIslandUtil;

@Component
public class IslandService {

	private final SkyblockIslandStorage skyblockIslandStorage;

	@Autowired
	public IslandService(SkyblockIslandStorage skyblockIslandStorage) {
		this.skyblockIslandStorage = skyblockIslandStorage;
	}

	public Optional<Island> findIslandAtLocation(Location location) {
		if (Settings.SkyblockIsland.ISLAND_WORLD.equals(location.getWorld())) {
			IslandCoordinates islandCoordinates = SkyblockIslandUtil.getIslandCoordinates(location);
			Optional<SkyblockIsland> islandOpt = skyblockIslandStorage.findSkyblockIslandByCoordinates(islandCoordinates);
			return Optional.ofNullable(islandOpt.orElse(null));
		}
		//TODO
		//guilds
		return Optional.empty();
	}

}
