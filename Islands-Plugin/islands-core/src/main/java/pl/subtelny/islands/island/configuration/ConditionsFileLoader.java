package pl.subtelny.islands.island.configuration;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.condition.*;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;

import java.io.File;
import java.util.List;
import java.util.Map;
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
        Map<String, AbstractFileParserStrategy<? extends Condition>> conditions = getConditions();
        return ConditionUtil.findConditionPaths(configuration, path).stream()
                .map(conditionPath -> loadCondition(path, conditions)).collect(Collectors.toList());
    }

    public List<CostCondition> loadCostConditions(String path) {
        Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditions = getCostConditions();
        return ConditionUtil.findCostConditionPaths(configuration, path).stream()
                .map(conditionPath -> loadCostCondition(conditionPath, costConditions)).collect(Collectors.toList());
    }

    private Map<String, AbstractFileParserStrategy<? extends Condition>> getConditions() {
        MoneyConditionFileParserStrategy moneyStrategy = new MoneyConditionFileParserStrategy(configuration, file, economy);
        ItemStackConditionFileParserStrategy itemStackStrategy = new ItemStackConditionFileParserStrategy(configuration, file);
        PermissionConditionFileParserStrategy permissionStrategy = new PermissionConditionFileParserStrategy(configuration, file);
        return Map.of("money", moneyStrategy,
                "item", itemStackStrategy,
                "permission", permissionStrategy);
    }

    private Map<String, AbstractFileParserStrategy<? extends CostCondition>> getCostConditions() {
        MoneyCostConditionFileParserStrategy moneyStrategy = new MoneyCostConditionFileParserStrategy(configuration, file, economy);
        ItemStackCostConditionFileParserStrategy itemStackStrategy = new ItemStackCostConditionFileParserStrategy(configuration, file);
        return Map.of("money", moneyStrategy,
                "item", itemStackStrategy);
    }

    private Condition loadCondition(String path, Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers) {
        return new ConditionFileParserStrategy(configuration, file, conditionParsers).load(path);
    }

    private CostCondition loadCostCondition(String path, Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers) {
        return new CostConditionFileParserStrategy(configuration, file, costConditionParsers).load(path);
    }

}
