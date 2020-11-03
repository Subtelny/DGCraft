package pl.subtelny.utilities.reward.command;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;
import pl.subtelny.utilities.Saveable;

import java.io.File;

public class CommandRewardFileParserStrategy extends AbstractFileParserStrategy<CommandReward> {

    public CommandRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public CommandReward load(String path) {
        String command = new ObjectFileParserStrategy<String>(configuration, file).load(path + ".command");
        return new CommandReward(command);
    }

    @Override
    public Saveable set(String path, CommandReward value) {
        throw new UnsupportedOperationException("Saving Command Reward is not implemented yet");
    }
}
