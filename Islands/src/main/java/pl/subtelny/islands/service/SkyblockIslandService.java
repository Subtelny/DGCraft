package pl.subtelny.islands.service;

import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.SkyblockIslandRepository;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.SkyblockIslandUtil;

@Component
public class SkyblockIslandService {

	private final SkyblockIslandRepository skyblockIslandRepository;

	@Autowired
	public SkyblockIslandService(SkyblockIslandRepository skyblockIslandRepository) {
		this.skyblockIslandRepository = skyblockIslandRepository;
	}

	public boolean canBuild(Player player, Location location) {
		if (location.getWorld().equals(Settings.SkyblockIsland.ISLAND_WORLD)) {
			IslandCoordinates islandCoordinates = SkyblockIslandUtil.getIslandCoordinates(location);

			Optional<SkyblockIsland> islandOpt = skyblockIslandRepository.findIsland(islandCoordinates);
			if (islandOpt.isPresent()) {
				SkyblockIsland island = islandOpt.get();
				return island.canBuild(player);
			}
		}
		return true;
	}

}
