package pl.subtelny.utilities.reward.command;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class PlayerCommandRewardFileParserStrategy extends PathAbstractFileParserStrategy<PlayerCommandReward> {

    public PlayerCommandRewardFileParserStrategy(File file) {
        super(file);
    }

    public PlayerCommandRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public String getPath() {
        return "player-command";
    }

    @Override
    public PlayerCommandReward load(String path) {
        String command = new ObjectFileParserStrategy<String>(configuration, file).load(path + "." + getPath());
        return new PlayerCommandReward(command);
    }

    @Override
    public Saveable set(String path, PlayerCommandReward value) {
        throw new UnsupportedOperationException("Saving Command Reward is not implemented yet");
    }
}
