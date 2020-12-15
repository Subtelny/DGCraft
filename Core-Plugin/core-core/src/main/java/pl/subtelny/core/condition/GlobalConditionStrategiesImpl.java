package pl.subtelny.core.condition;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class GlobalConditionStrategiesImpl implements GlobalConditionStrategies {

    private final EconomyProvider economyProvider;

    @Autowired
    public GlobalConditionStrategiesImpl(EconomyProvider economyProvider) {
        this.economyProvider = economyProvider;
    }

    @Override
    public List<PathAbstractFileParserStrategy<? extends Condition>> getGlobalConditionStrategies(File file) {
        return getConditions(file);
    }

    @Override
    public List<PathAbstractFileParserStrategy<? extends CostCondition>> getGlobalCostConditionStrategies(File file) {
        return getCostConditions(file);
    }

    private List<PathAbstractFileParserStrategy<? extends Condition>> getConditions(File file) {
        return Arrays.asList(
                new MoneyConditionFileParserStrategy(file, economyProvider.getEconomy()),
                new ItemStackConditionFileParserStrategy(file),
                new PermissionConditionFileParserStrategy(file));
    }

    private List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditions(File file) {
        return Arrays.asList(
                new MoneyCostConditionFileParserStrategy(file, economyProvider.getEconomy()),
                new ItemStackCostConditionFileParserStrategy(file));
    }

}
