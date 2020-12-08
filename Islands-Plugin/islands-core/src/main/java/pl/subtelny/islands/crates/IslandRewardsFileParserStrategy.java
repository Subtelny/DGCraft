package pl.subtelny.islands.crates;

import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.List;

public class IslandRewardsFileParserStrategy extends PathAbstractFileParserStrategy<IslandReward> {

    private final List<IslandRewardFileParserStrategy> islandRewardFileParserStrategies;

    public IslandRewardsFileParserStrategy(
            File file,
            List<IslandRewardFileParserStrategy> islandRewardFileParserStrategies) {
        super(file);
        this.islandRewardFileParserStrategies = islandRewardFileParserStrategies;
    }

    @Override
    public String getPath() {
        return "island";
    }

    @Override
    public IslandReward load(String path) {
        String type = configuration.getString(path + "." + getPath());
        return islandRewardFileParserStrategies.stream()
                .filter(islandRewardFileParserStrategy -> islandRewardFileParserStrategy.getIslandValue().equals(type))
                .map(islandRewardFileParserStrategy -> islandRewardFileParserStrategy.load(path))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found island reward strategy " + path + " v: " + type));
    }

    @Override
    public Saveable set(String path, IslandReward value) {
        throw new UnsupportedOperationException("Saving Island Reward is not implemented yet");
    }
}
