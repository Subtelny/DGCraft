package pl.subtelny.islands.service;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.island.IslandRepository;
import pl.subtelny.islands.repository.island.SkyblockIslandRepository;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class IslandService {

    private final SkyblockIslandRepository skyblockIslandRepository;

    private final IslandRepository islandRepository;

    private final IslanderService islanderService;

    @Autowired
    public IslandService(SkyblockIslandRepository skyblockIslandRepository,
                         IslandRepository islandRepository,
                         IslanderService islanderService) {
        this.skyblockIslandRepository = skyblockIslandRepository;
        this.islandRepository = islandRepository;
        this.islanderService = islanderService;
    }

    public boolean isInIsland(Player player, Island island) {
    	if(island.getIslandType() == IslandType.SKYBLOCK) {
			SkyblockIsland skyblockIsland = (SkyblockIsland) island;
			Islander islander = null;; //TODO islanderService.getIslander(player);
			return skyblockIsland.isInIsland(islander);
		}
    	return false;
    }

    public IslandFindResult findIslandAtLocation(Location location) {
        World world = location.getWorld();
        if (Settings.SkyblockIsland.ISLAND_WORLD.equals(world)) {
            IslandCoordinates islandCoordinates = SkyblockIslandUtil.getIslandCoordinates(location);
            return findSkyblockIsland(islandCoordinates);
        }
        if (Settings.GuildIsland.ISLAND_WORLD.equals(world)) {
            //TODO
            //guilds
        }
        return new IslandFindResult();
    }

    private IslandFindResult findSkyblockIsland(IslandCoordinates islandCoordinates) {
        CompletableFuture<Optional<Island>> future =
                JobsProvider.supplyAsync(() -> skyblockIslandRepository.findSkyblockIsland(islandCoordinates).map(island -> island));
        return new IslandFindResult(future);
    }

}
