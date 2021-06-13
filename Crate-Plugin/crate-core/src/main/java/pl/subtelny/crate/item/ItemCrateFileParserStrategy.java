package pl.subtelny.crate.item;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateWrapperParserStrategy;
import pl.subtelny.crate.type.BaseItemCrateFileParserStrategy;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;

public class ItemCrateFileParserStrategy extends AbstractFileParserStrategy<ItemCrate> {

    private final List<ItemCrateWrapperParserStrategy> itemCrateParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    public ItemCrateFileParserStrategy(YamlConfiguration configuration,
                                       File file,
                                       List<ItemCrateWrapperParserStrategy> itemCrateParsers,
                                       List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                       List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                       List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        super(configuration, file);
        this.itemCrateParsers = itemCrateParsers;
        this.rewardParsers = rewardParsers;
        this.costConditionParsers = costConditionParsers;
        this.conditionParsers = conditionParsers;
    }

    public ItemCrateFileParserStrategy(File file,
                                       List<ItemCrateWrapperParserStrategy> itemCrateParsers,
                                       List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                       List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                       List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        super(file);
        this.itemCrateParsers = itemCrateParsers;
        this.rewardParsers = rewardParsers;
        this.costConditionParsers = costConditionParsers;
        this.conditionParsers = conditionParsers;
    }

    @Override
    public ItemCrate load(String path) {
        BaseItemCrateFileParserStrategy strategy = new BaseItemCrateFileParserStrategy(configuration, file, rewardParsers, costConditionParsers, conditionParsers);
        BaseItemCrate basicItemCrate = strategy.load(path);

        ItemCrateConstructor builder = new ItemCrateConstructor(basicItemCrate, configuration, path);
        itemCrateParsers.forEach(builder::basedOn);
        return builder.get();
    }

    @Override
    public Saveable set(String path, ItemCrate value) {
        return null;
    }


    private static class ItemCrateConstructor {

        private ItemCrate itemCrate;

        private final YamlConfiguration config;

        private final String path;

        public ItemCrateConstructor(ItemCrate itemCrate, YamlConfiguration config, String path) {
            this.itemCrate = itemCrate;
            this.config = config;
            this.path = path;
        }

        public void basedOn(ItemCrateWrapperParserStrategy strategy) {
            itemCrate = strategy.create(itemCrate, config, path);
        }

        public ItemCrate get() {
            return itemCrate;
        }

    }

}
