package pl.subtelny.utilities.condition;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;
import java.util.Map;

public class ConditionFileParserStrategy extends AbstractFileParserStrategy<Condition> {

    private final Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers;

    public ConditionFileParserStrategy(YamlConfiguration configuration, File file,
                                       Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        super(configuration, file);
        this.conditionParsers = conditionParsers;
    }

    @Override
    public Condition load(String path) {
        return conditionParsers.entrySet().stream()
                .filter(entry -> configuration.contains(path + "." + entry.getKey()))
                .findAny()
                .map(entry -> entry.getValue().load(path))
                .orElseThrow(() -> new IllegalArgumentException("Not found any condition"));
    }

    @Override
    public Saveable set(String path, Condition value) {
        throw new UnsupportedOperationException("Saving Condition is not implemented yet");
    }
}
