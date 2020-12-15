package pl.subtelny.utilities.condition.itemstack;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;

public class ItemStackConditionFileParserStrategy extends PathAbstractFileParserStrategy<ItemStackCondition> {

    private final ItemStackFileParserStrategy parserStrategy;

    public ItemStackConditionFileParserStrategy(File file) {
        super(file);
        this.parserStrategy = new ItemStackFileParserStrategy(configuration, file);
    }

    public ItemStackConditionFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
        this.parserStrategy = new ItemStackFileParserStrategy(configuration, file);
    }

    @Override
    public String getPath() {
        return parserStrategy.getPath();
    }

    @Override
    public ItemStackCondition load(String path) {
        ItemStack itemStack = parserStrategy.load(path);
        return new ItemStackCondition(itemStack);
    }

    @Override
    public Saveable set(String path, ItemStackCondition value) {
        throw new UnsupportedOperationException("Saving ItemCondition is not implemented yet");
    }

}
