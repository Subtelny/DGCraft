package pl.subtelny.islands.module.crates.reward.open;

import pl.subtelny.islands.island.crates.IslandCrates;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class OpenIslandCrateRewardFileParserStrategy extends PathAbstractFileParserStrategy<OpenIslandCrateReward> {

    private final IslandCrates islandCrates;

    public OpenIslandCrateRewardFileParserStrategy(File file, IslandCrates islandCrates) {
        super(file);
        this.islandCrates = islandCrates;
    }

    @Override
    public OpenIslandCrateReward load(String path) {
        String crateName = configuration.getString(path + "." + getPath());
        return new OpenIslandCrateReward(islandCrates, crateName);
    }

    @Override
    public Saveable set(String path, OpenIslandCrateReward value) {
        throw new UnsupportedOperationException("Saving OpenIslandCrateReward is not implemented yet");
    }

    @Override
    public String getPath() {
        return "island-crate";
    }
}
