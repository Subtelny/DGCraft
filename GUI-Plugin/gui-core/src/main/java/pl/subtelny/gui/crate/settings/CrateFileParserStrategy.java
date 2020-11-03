package pl.subtelny.gui.crate.settings;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.crate.model.ItemCrate;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.Saveable;
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

    private final Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final Map<String, AbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final Plugin plugin;

    public CrateFileParserStrategy(YamlConfiguration configuration, File file,
                                   Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                   Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                   Map<String, AbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                   Plugin plugin) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
        this.plugin = plugin;
    }

    @Override
    public Crate load(String path) {
        CrateId crateId = CrateId.of(plugin, file.getName().split("\\.")[0]);
        int size = configuration.getInt("configuration.size");
        String title = configuration.getString("configuration.title");
        boolean global = configuration.getBoolean("configuration.global");
        Map<Integer, ItemCrate> itemCrates = createItemCrates();
        String permission = configuration.getString("configuration.permission");
        return new Crate(crateId, itemCrates, global, title, size, permission);
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
        CrateId open = ConfigUtil.getString(configuration, onclickPath + ".open").map(s -> CrateId.of(GUI.plugin, s)).orElse(null);
        ItemStack itemStack = new ItemStackFileParserStrategy(configuration, file).load(itemPath);
        return new ItemCrate(itemStack, conditions, costConditions, rewards, open);
    }

    private List<Condition> loadConditions(String path) {
        String conditionsPath = path + ".conditions";
        return ConditionUtil.findConditionPaths(configuration, conditionsPath).stream()
                .map(this::loadCondition).collect(Collectors.toList());
    }

    private List<CostCondition> loadCostConditions(String path) {
        String conditionsPath = path + ".conditions";
        return ConditionUtil.findCostConditionPaths(configuration, conditionsPath).stream()
                .map(this::loadCostCondition).collect(Collectors.toList());
    }

    private Condition loadCondition(String path) {
        return new ConditionFileParserStrategy(configuration, file, conditionParsers).load(path);
    }

    private CostCondition loadCostCondition(String path) {
        return new CostConditionFileParserStrategy(configuration, file, costConditionParsers).load(path);
    }

    private List<Reward> loadRewards(String path) {
        String rewardsPath = path + ".rewards";
        return getSectionKeys(configuration, rewardsPath).orElse(new HashSet<>()).stream()
                .map(s -> loadReward(rewardsPath + "." + s))
                .collect(Collectors.toList());

    }

    private Reward loadReward(String path) {
        return new RewardFileParserStrategy(configuration, file, rewardParsers).load(path);
    }

}
