package pl.subtelny.islands.island.crate.parser;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.islands.island.crate.IslandReward;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public abstract class IslandRewardFileParserStrategy extends PathAbstractFileParserStrategy<IslandReward> {

    public IslandRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    protected IslandRewardFileParserStrategy(File file) {
        super(file);
    }

    public abstract String getIslandValue();

}
