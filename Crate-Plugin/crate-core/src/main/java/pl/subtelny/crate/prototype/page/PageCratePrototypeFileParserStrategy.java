package pl.subtelny.crate.prototype.page;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeFileParserStrategy;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;

public class PageCratePrototypeFileParserStrategy extends CratePrototypeFileParserStrategy {

    public PageCratePrototypeFileParserStrategy(YamlConfiguration configuration,
                                                File file,
                                                List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                                List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                                Plugin plugin) {
        super(configuration, file, conditionParsers, costConditionParsers, rewardParsers, plugin);
    }

    public PageCratePrototypeFileParserStrategy(File file,
                                                   List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                   List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                                   List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                                   Plugin plugin) {
        super(file, conditionParsers, costConditionParsers, rewardParsers, plugin);
    }

    @Override
    public CratePrototype load(String path) {
        CratePrototype cratePrototype = super.load(path);
        ItemStack next = getControlPageItemStack(path + ".controls.next");
        ItemStack previous = getControlPageItemStack(path + ".controls.previous");
        return new PageCratePrototype(cratePrototype.getCrateId(),
                cratePrototype.getCrateType(),
                cratePrototype.getTitle(),
                cratePrototype.getPermission(),
                cratePrototype.getSize(),
                cratePrototype.getItems(),
                next,
                previous);
    }

    private ItemStack getControlPageItemStack(String path) {
        return new ItemStackFileParserStrategy(configuration, file).load(path);
    }

    @Override
    public Saveable set(String path, CratePrototype value) {
        throw new UnsupportedOperationException("Saving PageCratePrototype is not implemented yet");
    }
}
