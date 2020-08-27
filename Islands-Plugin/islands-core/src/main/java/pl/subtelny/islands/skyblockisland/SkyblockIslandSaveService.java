package pl.subtelny.islands.skyblockisland;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandSaver;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;

@Component
public class SkyblockIslandSaveService implements IslandSaver<SkyblockIsland> {

    @Override
    public void saveIsland(SkyblockIsland island) {

    }

    @Override
    public Class<SkyblockIsland> getIslandClass() {
        return SkyblockIsland.class;
    }

}
