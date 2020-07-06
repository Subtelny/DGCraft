package pl.subtelny.islands.service;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.repository.island.IslandFindResult;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class IslandsQueryService {

    private final SkyblockIslandRepository skyblockIslandRepository;

    @Autowired
    public IslandsQueryService(SkyblockIslandRepository skyblockIslandRepository) {
		this.skyblockIslandRepository = skyblockIslandRepository;
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
        return IslandFindResult.NOT_ISLAND_WORLD;
    }

    private IslandFindResult findSkyblockIsland(IslandCoordinates islandCoordinates) {
        CompletableFuture<Optional<Island>> future =
                JobsProvider.supplyAsync(() -> skyblockIslandRepository.findSkyblockIsland(islandCoordinates).map(island -> island));
        return new IslandFindResult(future);
    }

}
