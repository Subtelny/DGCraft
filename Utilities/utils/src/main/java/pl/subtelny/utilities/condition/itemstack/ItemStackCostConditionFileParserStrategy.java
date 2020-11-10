package pl.subtelny.utilities.condition.itemstack;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;

import java.io.File;

public class ItemStackCostConditionFileParserStrategy extends PathAbstractFileParserStrategy<ItemStackCostCondition> {

    private final ItemStackConditionFileParserStrategy parserStrategy;

    public ItemStackCostConditionFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
        this.parserStrategy = new ItemStackConditionFileParserStrategy(configuration, file);
    }

    protected ItemStackCostConditionFileParserStrategy(File file) {
        super(file);
        this.parserStrategy = new ItemStackConditionFileParserStrategy(configuration, file);
    }

    @Override
    public String getPath() {
        return parserStrategy.getPath();
    }

    @Override
    public ItemStackCostCondition load(String path) {
        ItemStackCondition itemCondition = parserStrategy.load(path);
        return new ItemStackCostCondition(itemCondition);
    }

    @Override
    public Saveable set(String path, ItemStackCostCondition value) {
        throw new UnsupportedOperationException("Saving ItemCostCondition is not implemented yet");
    }

}
