package pl.subtelny.core.condition;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.core.api.economy.EconomyProvider;
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
public class GlobalRewardStrategiesImpl implements GlobalRewardStrategies {

    private final EconomyProvider economyProvider;

    @Autowired
    public GlobalRewardStrategiesImpl(EconomyProvider economyProvider) {
        this.economyProvider = economyProvider;
    }

    @Override
    public List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers(File file) {
        return Arrays.asList(
                new ItemStackRewardFileParserStrategy(file),
                new MoneyRewardFileParserStrategy(file, economyProvider.getEconomy()),
                new CommandRewardFileParserStrategy(file),
                new PlayerCommandRewardFileParserStrategy(file)
        );
    }

}
