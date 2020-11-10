package pl.subtelny.utilities.condition;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.List;

public class CostConditionFileParserStrategy extends AbstractFileParserStrategy<CostCondition> {

    private final List<PathAbstractFileParserStrategy<? extends CostCondition>> conditionParsers;

    public CostConditionFileParserStrategy(YamlConfiguration configuration, File file,
                                           List<PathAbstractFileParserStrategy<? extends CostCondition>> conditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
    }

    protected CostConditionFileParserStrategy(File file, List<PathAbstractFileParserStrategy<? extends CostCondition>> conditionParsers) {
        super(file);
        this.conditionParsers = conditionParsers;
    }

    @Override
    public CostCondition load(String path) {
        return conditionParsers.stream()
                .filter(parserStrategy -> configuration.contains(path + "." + parserStrategy.getPath()))
                .findAny()
                .map(parserStrategy -> parserStrategy.load(path))
                .orElseThrow(() -> new IllegalArgumentException("Not found any condition"));
    }

    @Override
    public Saveable set(String path, CostCondition value) {
        throw new UnsupportedOperationException("Saving CostCondition is not implemented yet");
    }
}
