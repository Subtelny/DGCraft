package pl.subtelny.islands.island;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.island.repository.IslandFindResult;
import pl.subtelny.islands.skyblockisland.SkyblockIslandQueryService;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;
import pl.subtelny.islands.utils.SkyblockIslandUtil;

@Component
public class IslandsQueryService {

    private final SkyblockIslandSettings skyblockIslandSettings;

    private final SkyblockIslandQueryService skyblockIslandQueryService;

    @Autowired
    public IslandsQueryService(SkyblockIslandSettings skyblockIslandSettings, SkyblockIslandQueryService skyblockIslandQueryService) {
        this.skyblockIslandSettings = skyblockIslandSettings;
        this.skyblockIslandQueryService = skyblockIslandQueryService;
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
        SkyblockIsland island = skyblockIslandQueryService.findSkyblockIsland(islandCoordinates).orElse(null);
        return IslandFindResult.of(island);
    }

}
