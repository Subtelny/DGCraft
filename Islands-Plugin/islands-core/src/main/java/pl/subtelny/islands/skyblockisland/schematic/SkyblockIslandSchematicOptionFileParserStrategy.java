package pl.subtelny.islands.skyblockisland.schematic;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkyblockIslandSchematicOptionFileParserStrategy extends AbstractFileParserStrategy<SkyblockIslandSchematicOption> {

    private final Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers;

    private final Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers;

    public SkyblockIslandSchematicOptionFileParserStrategy(YamlConfiguration configuration, File file,
                                                           Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers,
                                                           Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
        this.costConditionParsers = costConditionParsers;
    }

    @Override
    public SkyblockIslandSchematicOption load(String path) {
        String[] split = path.split("\\.");
        String schematicFileName = split[split.length - 1];
        String schematicFilePath = file.getParentFile() + "/schematics/" + schematicFileName + ".schem";
        String name = configuration.getString(path + ".name");
        List<String> lore = configuration.getStringList(path + ".lore");
        boolean defaultSchematic = configuration.getBoolean(path + ".default");
        List<Condition> conditions = loadConditions(path);
        List<CostCondition> costConditions = loadCostConditions(path);
        return new SkyblockIslandSchematicOption(schematicFilePath, name, lore, defaultSchematic, conditions, costConditions);
    }

    @Override
    public Saveable set(String path, SkyblockIslandSchematicOption value) {
        throw new UnsupportedOperationException("Saving schematic option is not supported");
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
