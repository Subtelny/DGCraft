package pl.subtelny.islands.island.crate.open;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

import static pl.subtelny.utilities.ConfigUtil.getString;

public class IslandOpenCrateRewardFileParserStrategy extends PathAbstractFileParserStrategy<IslandOpenCrateReward> {

    private final IslandModule<? extends Island> islandModule;

    public IslandOpenCrateRewardFileParserStrategy(File file,
                                                   IslandModule<? extends Island> islandModule) {
        super(file);
        this.islandModule = islandModule;
    }

    @Override
    public IslandOpenCrateReward load(String path) {
        String fullPath = path + "." + getPath();
        String rawCrateId = getString(configuration, fullPath)
                .orElseThrow(() -> ValidationException.of("islandOpenCrateReward.value_not_found", file.getName(), fullPath));
        return new IslandOpenCrateReward(islandModule, rawCrateId);
    }

    @Override
    public Saveable set(String path, IslandOpenCrateReward value) {
        throw new UnsupportedOperationException("Saving IslandOpenCrate Reward is not implemented yet");
    }

    @Override
    public String getPath() {
        return "open-island-crate";
    }
}
