package pl.subtelny.islands.island;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.island.repository.IslandFindResult;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
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
        if (world.equals(skyblockIslandSettings.getWorld())) {
            return findSkyblockIsland(location);
        }
        return IslandFindResult.NOT_ISLAND_WORLD;
    }

    private IslandFindResult findSkyblockIsland(Location location) {
        IslandCoordinates islandCoordinates = SkyblockIslandUtil.getIslandCoordinates(location, skyblockIslandSettings.getMaxIslandSize());
        SkyblockIsland island = skyblockIslandRepository.findSkyblockIsland(islandCoordinates).orElse(null);
        return IslandFindResult.of(island);
    }

}
