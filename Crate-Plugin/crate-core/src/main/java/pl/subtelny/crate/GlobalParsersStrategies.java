package pl.subtelny.crate;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.crate.api.item.ItemCrateWrapperParserStrategy;
import pl.subtelny.crate.item.config.ConfigItemCrateWrapperParserStrategy;
import pl.subtelny.crate.item.controller.PageControllerItemCrateWrapperParserStrategy;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;
import pl.subtelny.utilities.reward.command.CommandRewardFileParserStrategy;
import pl.subtelny.utilities.reward.command.PlayerCommandRewardFileParserStrategy;
import pl.subtelny.utilities.reward.itemstack.ItemStackRewardFileParserStrategy;
import pl.subtelny.utilities.reward.money.MoneyRewardFileParserStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class GlobalParsersStrategies {

    private final EconomyProvider economyProvider;

    @Autowired
    public GlobalParsersStrategies(EconomyProvider economyProvider) {
        this.economyProvider = economyProvider;
    }

    public List<ItemCrateWrapperParserStrategy> getGlobalItemCrateWrapperParsers() {
        return Arrays.asList(
                new PageControllerItemCrateWrapperParserStrategy(),
                new ConfigItemCrateWrapperParserStrategy()
        );
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getGlobalRewardParsers(File file) {
        return Arrays.asList(
                new ItemStackRewardFileParserStrategy(file),
                new MoneyRewardFileParserStrategy(file, economyProvider.getEconomy()),
                new CommandRewardFileParserStrategy(file),
                new PlayerCommandRewardFileParserStrategy(file)
        );
    }

    public List<PathAbstractFileParserStrategy<? extends CostCondition>> getGlobalCostConditionParsers(File file) {
        return Arrays.asList(
                new MoneyCostConditionFileParserStrategy(file, economyProvider.getEconomy()),
                new ItemStackCostConditionFileParserStrategy(file)
        );
    }

    public List<PathAbstractFileParserStrategy<? extends Condition>> getGlobalConditionParsers(File file) {
        return Arrays.asList(
                new MoneyConditionFileParserStrategy(file, economyProvider.getEconomy()),
                new ItemStackConditionFileParserStrategy(file),
                new PermissionConditionFileParserStrategy(file)
        );
    }

}
