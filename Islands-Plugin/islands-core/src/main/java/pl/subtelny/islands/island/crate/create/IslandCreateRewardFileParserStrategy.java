package pl.subtelny.islands.island.crate.create;

import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.islands.island.crate.IslandReward;
import pl.subtelny.islands.island.crate.IslandRewardFileParserStrategy;
import pl.subtelny.islands.island.IslandCreateService;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.utilities.Saveable;

import java.io.File;

public class IslandCreateRewardFileParserStrategy extends IslandRewardFileParserStrategy {

    private final IslandType islandType;

    private final ComponentProvider componentProvider;

    public IslandCreateRewardFileParserStrategy(
            File file,
            IslandType islandType,
            ComponentProvider componentProvider) {
        super(file);
        this.islandType = islandType;
        this.componentProvider = componentProvider;
    }

    @Override
    public String getPath() {
        return "island";
    }

    @Override
    public String getIslandValue() {
        return "create";
    }

    @Override
    public IslandReward load(String path) {
        IslandCreateService islandCreateService = componentProvider.getComponent(IslandCreateService.class);
        return new IslandCreateReward(islandCreateService, islandType);
    }

    @Override
    public Saveable set(String path, IslandReward value) {
        throw new UnsupportedOperationException("Saving IslandCreate Reward is not implemented yet");
    }
}
