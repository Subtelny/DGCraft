package pl.subtelny.utilities.condition.itemstack;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;

public class ItemStackConditionFileParserStrategy extends AbstractFileParserStrategy<ItemStackCondition> {

    public ItemStackConditionFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    @Override
    public ItemStackCondition load(String path) {
        ItemStack itemStack = new ItemStackFileParserStrategy(configuration, file).load(path);
        return new ItemStackCondition(itemStack);
    }

    @Override
    public Saveable set(String path, ItemStackCondition value) {
        throw new UnsupportedOperationException("Saving ItemCondition is not implemented yet");
    }

}
