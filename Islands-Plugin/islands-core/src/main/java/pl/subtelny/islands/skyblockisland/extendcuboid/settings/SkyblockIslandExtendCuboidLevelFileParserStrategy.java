package pl.subtelny.islands.skyblockisland.extendcuboid.settings;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidLevel;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.ConditionFileParserStrategy;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.CostConditionFileParserStrategy;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.ConfigUtil.getStringList;

public class SkyblockIslandExtendCuboidLevelFileParserStrategy extends AbstractFileParserStrategy<SkyblockIslandExtendCuboidLevel> {

    private final Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    public SkyblockIslandExtendCuboidLevelFileParserStrategy(YamlConfiguration configuration, File file,
                                                             Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                             Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
    }

    @Override
    public SkyblockIslandExtendCuboidLevel load(String path) {
        int islandSize = configuration.getInt(path + ".island_size");
        List<Condition> conditions = loadConditions(path);
        List<CostCondition> costConditions = loadCostConditions(path);
        return new SkyblockIslandExtendCuboidLevel(islandSize, conditions, costConditions);
    }

    @Override
    public Saveable set(String path, SkyblockIslandExtendCuboidLevel value) {
        throw new UnsupportedOperationException("Saving extend cuboid level is not supported");
    }

    private List<Condition> loadConditions(String path) {
        String conditionsPath = path + ".conditions";
        return getStringList(configuration, conditionsPath)
                .map(keys -> keys.stream()
                        .map(key -> conditionsPath + "." + key)
                        .filter(conditionPath -> "CONDITION".equals(configuration.getString(conditionPath + ".type")))
                        .map(this::loadCondition)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("Not found condition for one of extend levels"));
    }

    private List<CostCondition> loadCostConditions(String path) {
        String conditionsPath = path + ".conditions";
        return getStringList(configuration, conditionsPath)
                .map(keys -> keys.stream()
                        .map(key -> conditionsPath + "." + key)
                        .filter(conditionPath -> "COST".equals(configuration.getString(conditionPath + ".type")))
                        .map(this::loadCostCondition)
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    private Condition loadCondition(String path) {
        return new ConditionFileParserStrategy(configuration, file, conditionParsers).load(path);
    }

    private CostCondition loadCostCondition(String path) {
        return new CostConditionFileParserStrategy(configuration, file, costConditionParsers).load(path);
    }

}
