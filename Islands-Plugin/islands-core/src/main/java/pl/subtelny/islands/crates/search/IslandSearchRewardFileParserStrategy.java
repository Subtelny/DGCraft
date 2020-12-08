package pl.subtelny.islands.crates.search;

import pl.subtelny.islands.crates.IslandReward;
import pl.subtelny.islands.crates.IslandRewardFileParserStrategy;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.utilities.Saveable;

import java.io.File;

public class IslandSearchRewardFileParserStrategy extends IslandRewardFileParserStrategy {

    private final IslandModule<? extends Island> islandModule;

    public IslandSearchRewardFileParserStrategy(
            File file,
            IslandModule<? extends Island> islandModule) {
        super(file);
        this.islandModule = islandModule;
    }

    @Override
    public String getPath() {
        return "island";
    }

    @Override
    public String getIslandValue() {
        return "search";
    }

    @Override
    public IslandReward load(String path) {
        return new IslandSearchReward(islandModule);
    }

    @Override
    public Saveable set(String path, IslandReward value) {
        throw new UnsupportedOperationException("Saving IslandCreate Reward is not implemented yet");
    }
}
