package pl.subtelny.gui.crate;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.crate.reward.OpenGuiRewardFileParserStrategy;
import pl.subtelny.gui.crate.settings.CrateFileParserStrategy;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CratesFileLoader {

    private final EconomyProvider economyProvider;

    private final PlayerCrateSessionService playerCrateSessionService;

    @Autowired
    public CratesFileLoader(EconomyProvider economyProvider, PlayerCrateSessionService playerCrateSessionService) {
        this.economyProvider = economyProvider;
        this.playerCrateSessionService = playerCrateSessionService;
    }

    public Crate loadCrate(CrateLoadRequest request) {
        Plugin plugin = request.getPlugin().orElse(GUI.plugin);
        File file = request.getFile();
        String prefix = request.getPrefix().orElse(null);
        List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = request.getRewardParsers();
        return loadCrateFromFile(file, prefix, plugin, rewardParsers);
    }

    private Crate loadCrateFromFile(File file, String prefix, Plugin plugin, List<PathAbstractFileParserStrategy<? extends Reward>> additionalRewardParsers) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        List<PathAbstractFileParserStrategy<? extends Condition>> conditionParsers = getConditionParsers(file, configuration);
        List<PathAbstractFileParserStrategy<? extends CostCondition>> costConditionParsers = getCostConditionParsers(file, configuration);
        List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = getRewardParsers(plugin, file, configuration);
        List<PathAbstractFileParserStrategy<? extends Reward>> allRewardParsers = Stream.of(additionalRewardParsers, rewardParsers).flatMap(Collection::stream).collect(Collectors.toList());
        return new CrateFileParserStrategy(configuration, file, conditionParsers, costConditionParsers, allRewardParsers, plugin, prefix).load("");
    }

    private List<PathAbstractFileParserStrategy<? extends Condition>> getConditionParsers(File file, YamlConfiguration configuration) {
        return Arrays.asList(
                new ItemStackConditionFileParserStrategy(configuration, file),
                new MoneyConditionFileParserStrategy(configuration, file, economyProvider.getEconomy()),
                new PermissionConditionFileParserStrategy(configuration, file));
    }

    private List<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditionParsers(File file, YamlConfiguration configuration) {
        return Arrays.asList(
                new ItemStackCostConditionFileParserStrategy(configuration, file),
                new MoneyCostConditionFileParserStrategy(configuration, file, economyProvider.getEconomy()));
    }

    private List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers(Plugin plugin, File file, YamlConfiguration configuration) {
        return Arrays.asList(
                new ItemStackRewardFileParserStrategy(configuration, file),
                new MoneyRewardFileParserStrategy(configuration, file, economyProvider.getEconomy()),
                new CommandRewardFileParserStrategy(configuration, file),
                new PlayerCommandRewardFileParserStrategy(configuration, file),
                new OpenGuiRewardFileParserStrategy(configuration, file, plugin, playerCrateSessionService)
        );
    }

}
