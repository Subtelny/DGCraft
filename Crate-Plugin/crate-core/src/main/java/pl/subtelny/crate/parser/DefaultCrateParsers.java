package pl.subtelny.crate.parser;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.economy.EconomyProvider;
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
public class DefaultCrateParsers {

    private final EconomyProvider economyProvider;

    @Autowired
    public DefaultCrateParsers(EconomyProvider economyProvider) {
        this.economyProvider = economyProvider;
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getDefaultRewardParsers(File file) {
        return Arrays.asList(
                new ItemStackRewardFileParserStrategy(file),
                new MoneyRewardFileParserStrategy(file, economyProvider.getEconomy()),
                new CommandRewardFileParserStrategy(file),
                new PlayerCommandRewardFileParserStrategy(file)
        );
    }

    public List<PathAbstractFileParserStrategy<? extends CostCondition>> getDefaultCostConditionParsers(File file) {
        return Arrays.asList(
                new MoneyCostConditionFileParserStrategy(file, economyProvider.getEconomy()),
                new ItemStackCostConditionFileParserStrategy(file)
        );
    }

    public List<PathAbstractFileParserStrategy<? extends Condition>> getDefaultConditionParsers(File file) {
        return Arrays.asList(
                new MoneyConditionFileParserStrategy(file, economyProvider.getEconomy()),
                new ItemStackConditionFileParserStrategy(file),
                new PermissionConditionFileParserStrategy(file)
        );
    }

}
