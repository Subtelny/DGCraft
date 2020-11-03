package pl.subtelny.utilities.condition;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.Saveable;

import java.io.File;
import java.util.Map;

public class CostConditionFileParserStrategy extends AbstractFileParserStrategy<CostCondition> {

    private final Map<String, AbstractFileParserStrategy<? extends CostCondition>> conditionParsers;

    public CostConditionFileParserStrategy(YamlConfiguration configuration, File file,
                                           Map<String, AbstractFileParserStrategy<? extends CostCondition>> conditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
    }

    protected CostConditionFileParserStrategy(File file, Map<String,
            AbstractFileParserStrategy<? extends CostCondition>> conditionParsers) {
        super(file);
        this.conditionParsers = conditionParsers;
    }

    @Override
    public CostCondition load(String path) {
        return conditionParsers.entrySet().stream()
                .filter(entry -> configuration.contains(path + "." + entry.getKey()))
                .findAny()
                .map(entry -> entry.getValue().load(path))
                .orElseThrow(() -> new IllegalArgumentException("Not found any condition"));
    }

    @Override
    public Saveable set(String path, CostCondition value) {
        throw new UnsupportedOperationException("Saving CostCondition is not implemented yet");
    }
}
