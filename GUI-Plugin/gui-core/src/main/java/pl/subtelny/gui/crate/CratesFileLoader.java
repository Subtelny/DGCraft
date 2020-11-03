package pl.subtelny.gui.crate;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.crate.model.Crate;
import pl.subtelny.gui.crate.settings.CrateFileParserStrategy;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;
import pl.subtelny.utilities.reward.command.CommandRewardFileParserStrategy;
import pl.subtelny.utilities.reward.command.PlayerCommandRewardFileParserStrategy;
import pl.subtelny.utilities.reward.itemstack.ItemStackRewardFileParserStrategy;
import pl.subtelny.utilities.reward.money.MoneyRewardFileParserStrategy;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CratesFileLoader {

    private final EconomyProvider economyProvider;

    @Autowired
    public CratesFileLoader(EconomyProvider economyProvider) {
        this.economyProvider = economyProvider;
    }

    public List<Crate> loadAllCratesFromDirectory(File dir, Plugin plugin) {
        File[] files = dir.listFiles();
        if (files != null) {
            return Arrays.stream(files).map(file -> loadCrateFromFile(file, plugin)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<Crate> loadAllCratesFromDirectory(File dir) {
        return loadAllCratesFromDirectory(dir, GUI.plugin);
    }

    public Crate loadCrateFromFile(File file) {
        return loadCrateFromFile(file, GUI.plugin);
    }

    public Crate loadCrateFromFile(File file, Plugin plugin) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        Map<String, AbstractFileParserStrategy<? extends Condition>> conditionParsers = getConditionParsers(file, configuration);
        Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditionParsers = getCostConditionParsers(file, configuration);
        Map<String, AbstractFileParserStrategy<? extends Reward>> rewardParsers = getRewardParsers(file, configuration);
        return new CrateFileParserStrategy(configuration, file, conditionParsers, costConditionParsers, rewardParsers, plugin).load("");
    }

    private Map<String, AbstractFileParserStrategy<? extends Condition>> getConditionParsers(File file, YamlConfiguration configuration) {
        return Map.of(
                "item", new ItemStackConditionFileParserStrategy(configuration, file),
                "money", new MoneyConditionFileParserStrategy(configuration, file, economyProvider.getEconomy()),
                "permission", new PermissionConditionFileParserStrategy(configuration, file));
    }

    private Map<String, AbstractFileParserStrategy<? extends CostCondition>> getCostConditionParsers(File file, YamlConfiguration configuration) {
        return Map.of(
                "item", new ItemStackCostConditionFileParserStrategy(configuration, file),
                "money", new MoneyCostConditionFileParserStrategy(configuration, file, economyProvider.getEconomy()));
    }

    private Map<String, AbstractFileParserStrategy<? extends Reward>> getRewardParsers(File file, YamlConfiguration configuration) {
        return Map.of(
                "item", new ItemStackRewardFileParserStrategy(configuration, file),
                "money", new MoneyRewardFileParserStrategy(configuration, file, economyProvider.getEconomy()),
                "command", new CommandRewardFileParserStrategy(configuration, file),
                "player-command", new PlayerCommandRewardFileParserStrategy(configuration, file)
        );
    }

}
