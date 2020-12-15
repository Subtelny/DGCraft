package pl.subtelny.utilities.reward.command;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class CommandRewardFileParserStrategy extends PathAbstractFileParserStrategy<CommandReward> {

    public CommandRewardFileParserStrategy(File file) {
        super(file);
    }

    public CommandRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public String getPath() {
        return "command";
    }

    @Override
    public CommandReward load(String path) {
        String command = new ObjectFileParserStrategy<String>(configuration, file).load(path + "." + getPath());
        return new CommandReward(command);
    }

    @Override
    public Saveable set(String path, CommandReward value) {
        throw new UnsupportedOperationException("Saving Command Reward is not implemented yet");
    }
}
