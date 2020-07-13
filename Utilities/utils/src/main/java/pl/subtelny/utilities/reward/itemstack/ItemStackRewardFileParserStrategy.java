package pl.subtelny.utilities.reward.itemstack;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;

public class ItemStackRewardFileParserStrategy extends AbstractFileParserStrategy<ItemStackReward> {

    public ItemStackRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public ItemStackReward load(String path) {
        ItemStack itemStack = new ItemStackFileParserStrategy(configuration, file).load(path);
        return new ItemStackReward(itemStack);
    }

    @Override
    public Saveable set(String path, ItemStackReward value) {
        throw new UnsupportedOperationException("Saving ItemStack Reward is not implemented yet");
    }
}
