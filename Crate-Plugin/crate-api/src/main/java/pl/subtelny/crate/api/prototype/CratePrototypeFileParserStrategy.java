package pl.subtelny.crate.api.prototype;

import org.bukkit.plugin.Plugin;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CratePrototypeFileParserStrategy extends AbstractFileParserStrategy<CratePrototype> {

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final String cratePrefix;

    private final Plugin plugin;

    public CratePrototypeFileParserStrategy(File file,
                                            List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                            List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers,
                                            List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers,
                                            String cratePrefix,
                                            Plugin plugin) {
        super(file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
        this.rewardParsers = rewardParsers;
        this.cratePrefix = cratePrefix;
        this.plugin = plugin;
    }

    @Override
    public CratePrototype load(String path) {
        String title = configuration.getString("configuration.title");
        String permission = configuration.getString("configuration.permission");
        int size = configuration.getInt("configuration.size");
        CrateType crateType = new CrateType(configuration.getString("configuration.type"));
        CrateId crateId = getCrateId();

        Map<Integer, ItemCratePrototype> content = ConfigUtil.getSectionKeys(configuration, "content")
                .map(this::getContent)
                .orElse(new HashMap<>());
        return new CratePrototype(crateId, crateType, title, permission, size, content);
    }

    private Map<Integer, ItemCratePrototype> getContent(Set<String> strings) {
        ItemCratePrototypeFileParserStrategy strategy = new ItemCratePrototypeFileParserStrategy(configuration, file, conditionParsers, costConditionParsers, rewardParsers);
        return strings.stream()
                .collect(Collectors.toMap(Integer::parseInt, s -> strategy.load("content" + "." + s)));
    }

    private CrateId getCrateId() {
        String crateName = file.getName().replace(".yml", "");
        String rawCrateId = cratePrefix != null ? String.join("-", cratePrefix, crateName) : crateName;
        return new CrateId(plugin, rawCrateId);
    }

    @Override
    public Saveable set(String path, CratePrototype value) {
        throw new UnsupportedOperationException("Saving CratePrototype is not implemented yet");
    }
}
