package pl.subtelny.utilities.reward.command;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.itemstack.ItemStackReward;

import java.io.File;

public class CommandRewardFileParserStrategy extends AbstractFileParserStrategy<CommandReward> {

    public CommandRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public CommandReward load(String path) {
        String command = new ObjectFileParserStrategy<String>(configuration, file).load(path);
        return new CommandReward(command);
    }

    @Override
    public Saveable set(String path, CommandReward value) {
        throw new UnsupportedOperationException("Saving Command Reward is not implemented yet");
    }
}
