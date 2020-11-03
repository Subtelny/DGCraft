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

public class IslandExtendLevelFileParserStrategy extends AbstractFileParserStrategy<IslandExtendLevel> {

    private final Economy economy;

    public IslandExtendLevelFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    @Override
    public IslandExtendLevel load(String path) {
        Integer size = new ObjectFileParserStrategy<Integer>(configuration, file).load(path + ".size");
        ConditionsFileLoader conditionsFileLoader = new ConditionsFileLoader(configuration, file, economy);
        List<Condition> conditions = conditionsFileLoader.loadConditions(path + ".conditions");
        List<CostCondition> costConditions = conditionsFileLoader.loadCostConditions(path + ".conditions");
        return new IslandExtendLevel(size, conditions, costConditions);
    }

    @Override
    public Saveable set(String path, IslandExtendLevel value) {
        throw new UnsupportedOperationException("Saving ExtendLevel is not implemented");
    }
}
