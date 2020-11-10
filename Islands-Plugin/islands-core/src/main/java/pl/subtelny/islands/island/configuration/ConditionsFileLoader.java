package pl.subtelny.islands.island.configuration;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConditionsFileLoader {

    private final YamlConfiguration configuration;

    private final File file;

    private final Economy economy;

    public ConditionsFileLoader(YamlConfiguration configuration, File file, Economy economy) {
        this.configuration = configuration;
        this.file = file;
        this.economy = economy;
    }

    public List<Condition> loadConditions(String path) {
        List<PathAbstractFileParserStrategy<? extends Condition>> conditions = getConditions();
        ConditionFileParserStrategy parserStrategy = new ConditionFileParserStrategy(configuration, file, conditions);
        return ConditionUtil.findConditionPaths(configuration, path).stream()
                .map(parserStrategy::load)
                .collect(Collectors.toList());
    }

    public List<CostCondition> loadCostConditions(String path) {
        List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditions = getCostConditions();
        CostConditionFileParserStrategy parserStrategy = new CostConditionFileParserStrategy(configuration, file, costConditions);
        return ConditionUtil.findCostConditionPaths(configuration, path).stream()
                .map(parserStrategy::load)
                .collect(Collectors.toList());
    }

    private List<PathAbstractFileParserStrategy<? extends Condition>> getConditions() {
        return Arrays.asList(
                new MoneyConditionFileParserStrategy(configuration, file, economy),
                new ItemStackConditionFileParserStrategy(configuration, file),
                new PermissionConditionFileParserStrategy(configuration, file));
    }

    private List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditions() {
        return Arrays.asList(
                new MoneyCostConditionFileParserStrategy(configuration, file, economy),
                new ItemStackCostConditionFileParserStrategy(configuration, file));
    }

}
