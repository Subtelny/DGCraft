package pl.subtelny.utilities.condition.itemstack;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;

public class ItemStackCostConditionFileParserStrategy extends AbstractFileParserStrategy<ItemStackCostCondition> {

    public ItemStackCostConditionFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    protected ItemStackCostConditionFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public ItemStackCostCondition load(String path) {
        ItemStackCondition itemCondition = new ItemStackConditionFileParserStrategy(configuration, file).load(path);
        return new ItemStackCostCondition(itemCondition);
    }

    @Override
    public Saveable set(String path, ItemStackCostCondition value) {
        throw new UnsupportedOperationException("Saving ItemCostCondition is not implemented yet");
    }

}
