package pl.subtelny.islands.service;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.repository.island.IslandFindResult;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandSettings;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class IslandsQueryService {

    private final SkyblockIslandSettings skyblockIslandSettings;

    private final SkyblockIslandRepository skyblockIslandRepository;

    @Autowired
    public IslandsQueryService(SkyblockIslandSettings skyblockIslandSettings, SkyblockIslandRepository skyblockIslandRepository) {
        this.skyblockIslandSettings = skyblockIslandSettings;
        this.skyblockIslandRepository = skyblockIslandRepository;
    }

    public IslandFindResult findIslandAtLocation(Location location) {
        World world = location.getWorld();
        if (skyblockIslandSettings.getWorld().equals(world)) {
            return findSkyblockIsland(location);
        }
        return IslandFindResult.NOT_ISLAND_WORLD;
    }

    private IslandFindResult findSkyblockIsland(Location location) {
        IslandCoordinates islandCoordinates = SkyblockIslandUtil.getIslandCoordinates(location, skyblockIslandSettings.getMaxIslandSize());
        CompletableFuture<Optional<Island>> future =
                JobsProvider.supplyAsync(() -> skyblockIslandRepository.findSkyblockIsland(islandCoordinates).map(island -> island));
        return new IslandFindResult(future);
    }

}
