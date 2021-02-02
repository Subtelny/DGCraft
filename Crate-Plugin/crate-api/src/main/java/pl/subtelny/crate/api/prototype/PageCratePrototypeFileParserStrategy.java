package pl.subtelny.crate.api.prototype;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PageCratePrototypeFileParserStrategy extends CratePrototypeFileParserStrategy {

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    public PageCratePrototypeFileParserStrategy(File file,
                                                List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                                List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                                String cratePrefix,
                                                Plugin plugin) {
        super(file, conditionParsers, costConditionParsers, rewardParsers, cratePrefix, plugin);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
    }

    @Override
    public CratePrototype load(String path) {
        CratePrototype cratePrototype = super.load(path);
        ItemStack next = getControlPageItemStack("controls.next");
        ItemStack previous = getControlPageItemStack("controls.previous");
        Map<Integer, ItemCratePrototype> staticContent = getStaticContent();
        return new PageCratePrototype(cratePrototype.getCrateId(),
                cratePrototype.getCrateType(),
                cratePrototype.getTitle(),
                cratePrototype.getPermission(),
                cratePrototype.getSize(),
                cratePrototype.getItems(),
                previous,
                next,
                staticContent);
    }

    private Map<Integer, ItemCratePrototype> getStaticContent() {
        return ConfigUtil.getSectionKeys(configuration, "static-content")
                .map(this::getStaticContent)
                .orElse(new HashMap<>());
    }

    private Map<Integer, ItemCratePrototype> getStaticContent(Set<String> strings) {
        ItemCratePrototypeFileParserStrategy strategy = new ItemCratePrototypeFileParserStrategy(configuration, file, conditionParsers, costConditionParsers, rewardParsers);
        return strings.stream()
                .collect(Collectors.toMap(Integer::parseInt, s -> strategy.load("static-content" + "." + s)));
    }

    private ItemStack getControlPageItemStack(String path) {
        return new ItemStackFileParserStrategy(configuration, file).load(path);
    }

    @Override
    public Saveable set(String path, CratePrototype value) {
        throw new UnsupportedOperationException("Saving PageCratePrototype is not implemented yet");
    }
}
