package pl.subtelny.islands.island.crate.parser;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototypeFileParserStrategy;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.crate.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;

public class IslandPageSampleItemCratePrototypeFileParserStrategy extends PageCratePrototypeFileParserStrategy {

    private final String cratePrefix;

    public IslandPageSampleItemCratePrototypeFileParserStrategy(File file,
                                                                List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                                List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                                                List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                                                String cratePrefix,
                                                                Plugin plugin) {
        super(file, conditionParsers, costConditionParsers, rewardParsers, cratePrefix, plugin);
        this.cratePrefix = cratePrefix;
    }

    @Override
    public CratePrototype load(String path) {
        PageCratePrototype cratePrototype = (PageCratePrototype) super.load(path);
        ItemStack searchSampleItemStack = getSearchSampleItemStack();
        return new IslandSearchCratePrototype(
                cratePrototype.getCrateId(),
                cratePrototype.getTitle(),
                cratePrototype.getItems(),
                cratePrototype.getPreviousPageItemStack(),
                cratePrototype.getNextPageItemStack(),
                searchSampleItemStack,
                getIslandType(),
                cratePrototype.getStaticItems()
        );
    }

    private IslandType getIslandType() {
        return new IslandType(cratePrefix);
    }

    private ItemStack getSearchSampleItemStack() {
        return new ItemStackFileParserStrategy(configuration, file).load("sample-item");
    }

    @Override
    public Saveable set(String path, CratePrototype value) {
        throw new UnsupportedOperationException("Saving IslandSearchCratePrototype is not implemented yet");
    }
}
