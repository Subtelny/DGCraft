package pl.subtelny.islands.creator;

import pl.subtelny.components.api.Autowired;
import pl.subtelny.components.api.Component;
import pl.subtelny.islands.repository.island.SkyblockIslandRepository;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;

@Component
public class IslandCreator {

    private final SkyblockIslandRepository skyblockIslandRepository;

    @Autowired
    public IslandCreator(SkyblockIslandRepository skyblockIslandRepository) {
        this.skyblockIslandRepository = skyblockIslandRepository;
    }

    public Island createIsland(IslandCreateRequest request) {
		IslandCreatorStrategy strategy = getIslandCreatorStrategyByRequest(request);
		return strategy.createIsland();
    }

    private IslandCreatorStrategy getIslandCreatorStrategyByRequest(IslandCreateRequest request) {
        if (request.getIslandType() == IslandType.SKYBLOCK) {
            return prepareSkyblockIslandCreatorStrategy((SkyblockIslandCreateRequest) request);
        }
        throw new IllegalArgumentException("Not found island creator strategy for type " + request.getIslandType());
    }

    private SkyblockIslandCreatorStrategy prepareSkyblockIslandCreatorStrategy(SkyblockIslandCreateRequest request) {
        return new SkyblockIslandCreatorStrategy(request, skyblockIslandRepository);
    }

}
