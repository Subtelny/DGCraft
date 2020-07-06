package pl.subtelny.islands.creator;

import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.model.island.Island;

public class SkyblockIslandCreatorStrategy implements IslandCreatorStrategy {

    private final SkyblockIslandCreateRequest request;

    private final SkyblockIslandRepository skyblockIslandRepository;

    public SkyblockIslandCreatorStrategy(SkyblockIslandCreateRequest request,
                                         SkyblockIslandRepository skyblockIslandRepository) {
        this.request = request;
        this.skyblockIslandRepository = skyblockIslandRepository;
    }

    @Override
    public Island createIsland() {
        return null;
    }
}
