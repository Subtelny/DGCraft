package pl.subtelny.utilities.reward;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;
import java.util.Map;

public class RewardFileParserStrategy extends AbstractFileParserStrategy<Reward> {

    private final Map<String, AbstractFileParserStrategy<? extends Reward>> rewardParsers;

    public RewardFileParserStrategy(YamlConfiguration configuration, File file,
                                    Map<String, AbstractFileParserStrategy<? extends Reward>> rewardParsers) {
        super(configuration, file);
        this.rewardParsers = rewardParsers;
    }

    @Override
    public Reward load(String path) {
        return rewardParsers.entrySet().stream()
                .filter(entry -> configuration.contains(path + "." + entry.getKey()))
                .findAny()
                .map(entry -> entry.getValue().load(path))
                .orElseThrow(() -> new IllegalArgumentException("Not found any reward"));
    }

    @Override
    public Saveable set(String path, Reward value) {
        throw new UnsupportedOperationException("Saving Reward is not implemented yet");
    }
}
