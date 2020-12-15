package pl.subtelny.utilities.reward.itemstack;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;

public class ItemStackRewardFileParserStrategy extends PathAbstractFileParserStrategy<ItemStackReward> {

    private final ItemStackFileParserStrategy parserStrategy;

    public ItemStackRewardFileParserStrategy(File file) {
        super(file);
        this.parserStrategy = new ItemStackFileParserStrategy(configuration, file);
    }

    public ItemStackRewardFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
        this.parserStrategy = new ItemStackFileParserStrategy(configuration, file);
    }

    @Override
    public String getPath() {
        return parserStrategy.getPath();
    }

    @Override
    public ItemStackReward load(String path) {
        ItemStack itemStack = parserStrategy.load(path);
        return new ItemStackReward(itemStack);
    }

    @Override
    public Saveable set(String path, ItemStackReward value) {
        throw new UnsupportedOperationException("Saving ItemStack Reward is not implemented yet");
    }
}
