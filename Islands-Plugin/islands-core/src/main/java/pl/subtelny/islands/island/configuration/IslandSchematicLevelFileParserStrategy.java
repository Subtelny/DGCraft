package pl.subtelny.islands.island.configuration;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.List;

public class IslandSchematicLevelFileParserStrategy extends AbstractFileParserStrategy<IslandSchematicLevel> {

    private final Economy economy;

    public IslandSchematicLevelFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    @Override
    public IslandSchematicLevel load(String path) {
        String schematicFileName = new ObjectFileParserStrategy<String>(configuration, file).load(path + ".schematic-file-name");
        ConditionsFileLoader conditionsFileLoader = new ConditionsFileLoader(configuration, file, economy);
        List<Condition> conditions = conditionsFileLoader.loadConditions(path + ".conditions");
        List<CostCondition> costConditions = conditionsFileLoader.loadCostConditions(path + ".conditions");
        return new IslandSchematicLevel(schematicFileName, conditions, costConditions);
    }

    @Override
    public Saveable set(String path, IslandSchematicLevel value) {
        throw new UnsupportedOperationException("Saving SchematicLevel is not implemented");
    }
}
