package pl.subtelny.gui.crate.settings;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.crate.model.CrateImpl2;
import pl.subtelny.gui.api.crate.model.ItemCrate;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;
import pl.subtelny.utilities.reward.RewardFileParserStrategy;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.ConfigUtil.getSectionKeys;

public class CrateFileParserStrategy extends AbstractFileParserStrategy<Crate> {

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final Plugin plugin;

    private final String prefix;

    public CrateFileParserStrategy(YamlConfiguration configuration, File file,
                                   List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                   List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                   List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                   Plugin plugin,
                                   String prefix) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
        this.plugin = plugin;
        this.prefix = prefix;
    }

    @Override
    public Crate load(String path) {
        String fileName = file.getName().split("\\.")[0];
        String rawCrateId = String.join("-", prefix, fileName);
        CrateId crateId = CrateId.of(plugin, rawCrateId);
        Map<Integer, ItemCrate> itemCrates = createItemCrates();
        int size = configuration.getInt("configuration.size");
        boolean global = configuration.getBoolean("configuration.global");
        String title = configuration.getString("configuration.title");
        String permission = configuration.getString("configuration.permission");
        return new CrateImpl2(crateId, itemCrates, global, title, size, permission);
    }

    @Override
    public Saveable set(String path, Crate value) {
        throw new UnsupportedOperationException("Saving Crate is not implemented yet");
    }

    private Map<Integer, ItemCrate> createItemCrates() {
        String contentPath = "content";
        Set<String> content = getSectionKeys(configuration, contentPath).orElse(new HashSet<>());
        return content.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toMap(slot -> slot, slot -> createItemCrate(contentPath + "." + slot)));
    }

    private ItemCrate createItemCrate(String itemPath) {
        String onclickPath = itemPath + ".onclick";
        List<Condition> conditions = loadConditions(onclickPath);
        List<CostCondition> costConditions = loadCostConditions(onclickPath);
        List<Reward> rewards = loadRewards(onclickPath);
        ItemStack itemStack = new ItemStackFileParserStrategy(configuration, file).load(itemPath);
        boolean closeAfterClick = ConfigUtil.getBoolean(configuration, itemPath + ".close-after-click").orElse(false);
        return new ItemCrate(itemStack, conditions, costConditions, rewards, closeAfterClick);
    }

    private List<Condition> loadConditions(String path) {
        String conditionsPath = path + ".conditions";
        ConditionFileParserStrategy parserStrategy = new ConditionFileParserStrategy(configuration, file, conditionParsers);
        return ConditionUtil.findConditionPaths(configuration, conditionsPath).stream()
                .map(parserStrategy::load)
                .collect(Collectors.toList());
    }

    private List<CostCondition> loadCostConditions(String path) {
        String conditionsPath = path + ".conditions";
        CostConditionFileParserStrategy parserStrategy = new CostConditionFileParserStrategy(configuration, file, costConditionParsers);
        return ConditionUtil.findCostConditionPaths(configuration, conditionsPath).stream()
                .map(parserStrategy::load)
                .collect(Collectors.toList());
    }

    private List<Reward> loadRewards(String path) {
        String rewardsPath = path + ".rewards";
        RewardFileParserStrategy rewardFileParserStrategy = new RewardFileParserStrategy(configuration, file, rewardParsers);
        return getSectionKeys(configuration, rewardsPath)
                .orElse(new HashSet<>()).stream()
                .map(sectionPath -> rewardsPath + "." + sectionPath)
                .map(rewardFileParserStrategy::load)
                .collect(Collectors.toList());

    }

}
