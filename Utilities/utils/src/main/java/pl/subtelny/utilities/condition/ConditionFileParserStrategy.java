package pl.subtelny.utilities.condition;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.List;

public class ConditionFileParserStrategy extends AbstractFileParserStrategy<Condition> {

    private final List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers;

    public ConditionFileParserStrategy(YamlConfiguration configuration, File file,
                                       List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
    }

    @Override
    public Condition load(String path) {
        return conditionParsers.stream()
                .filter(parserStrategy -> configuration.contains(path + "." + parserStrategy.getPath()))
                .findAny()
                .map(parserStrategy -> parserStrategy.load(path))
                .orElse(null);
    }

    @Override
    public Saveable set(String path, Condition value) {
        throw new UnsupportedOperationException("Saving Condition is not implemented yet");
    }
}
