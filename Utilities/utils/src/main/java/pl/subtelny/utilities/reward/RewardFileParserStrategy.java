package pl.subtelny.utilities.reward;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.List;

public class RewardFileParserStrategy extends AbstractFileParserStrategy<Reward> {

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    public RewardFileParserStrategy(YamlConfiguration configuration, File file, List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
        super(configuration, file);
        this.rewardParsers = rewardParsers;
    }

    public RewardFileParserStrategy(File file, List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers) {
        super(file);
        this.rewardParsers = rewardParsers;
    }

    @Override
    public Reward load(String path) {
        return rewardParsers.stream()
                .filter(parserStrategy -> configuration.contains(path + "." + parserStrategy.getPath()))
                .findAny()
                .map(parserStrategy -> parserStrategy.load(path))
                .orElseThrow(() -> new IllegalStateException("Not found any reward at path " + path + ", " + file.getName()));
    }

    @Override
    public Saveable set(String path, Reward value) {
        throw new UnsupportedOperationException("Saving Reward is not implemented yet");
    }
}
