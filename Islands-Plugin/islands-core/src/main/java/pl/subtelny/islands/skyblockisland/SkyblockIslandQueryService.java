package pl.subtelny.islands.skyblockisland;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.islandold.repository.IslandRepository;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.storage.SkyblockIslandCache;
import java.util.Optional;

@Component
public class SkyblockIslandQueryService {

    private final SkyblockIslandCache skyblockIslandCache;

    private final IslandRepository islandRepository;

    @Autowired
    public SkyblockIslandQueryService(SkyblockIslandCache skyblockIslandCache, IslandRepository islandRepository) {
        this.skyblockIslandCache = skyblockIslandCache;
        this.islandRepository = islandRepository;
    }

    public Optional<SkyblockIsland> findSkyblockIsland(IslandCoordinates islandCoordinates) {
        return skyblockIslandCache.findIslandId(islandCoordinates)
                .flatMap(this::findSkyblockIsland);
    }

    public Optional<SkyblockIsland> findSkyblockIsland(IslandId islandId) {
        return islandRepository.findIsland(islandId)
                .filter(island -> SkyblockIsland.TYPE.equals(island.getType()))
                .map(island -> (SkyblockIsland) island);
    }

}
