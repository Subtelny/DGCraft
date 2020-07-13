package pl.subtelny.islands.skyblockisland.extendcuboid.settings;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.ConfigUtil.getSectionKeys;

public class SkyblockIslandExtendCuboidOptionFileParserStrategy extends AbstractFileParserStrategy<SkyblockIslandExtendCuboidOption> {

    private final Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    public SkyblockIslandExtendCuboidOptionFileParserStrategy(YamlConfiguration configuration, File file,
                                                              Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                              Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
    }

    @Override
    public SkyblockIslandExtendCuboidOption load(String path) {
        int islandSize = configuration.getInt(path + ".island_size");
        List<Condition> conditions = loadConditions(path);
        List<CostCondition> costConditions = loadCostConditions(path);
        return new SkyblockIslandExtendCuboidOption(islandSize, conditions, costConditions);
    }

    @Override
    public Saveable set(String path, SkyblockIslandExtendCuboidOption value) {
        throw new UnsupportedOperationException("Saving extend cuboid level is not supported");
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

}
