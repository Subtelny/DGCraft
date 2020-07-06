package pl.subtelny.islands.skyblockisland.extendcuboid.settings;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.islands.skyblockisland.condition.SkyblockIslandPointsConditionFileParserStrategy;
import pl.subtelny.islands.skyblockisland.extendcuboid.SkyblockIslandExtendCuboidLevel;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SkyblockIslandExtendCuboidSettingsLoader {

    private final static String EXTENDS_PATH = "extends";

    private final File file;

    private final YamlConfiguration configuration;

    private final Economy economy;

    private final IslanderRepository islanderRepository;

    public SkyblockIslandExtendCuboidSettingsLoader(File file, Economy economy, IslanderRepository islanderRepository) {
        this.file = file;
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.economy = economy;
        this.islanderRepository = islanderRepository;
    }

    public Map<Integer, SkyblockIslandExtendCuboidLevel> loadExtendLevels() {
        List<Integer> levels = getLevels();
        return levels.stream()
                .collect(Collectors.toMap(level -> level, level -> {
                    String path = EXTENDS_PATH + "." + level;
                    return new SkyblockIslandExtendCuboidLevelFileParserStrategy(configuration, file, getConditions(), getCostConditions()).load(path);
                }));
    }

    private Map<String, AbstractFileParserStrategy<? extends Condition>> getConditions() {
        MoneyConditionFileParserStrategy moneyStrategy = new MoneyConditionFileParserStrategy(configuration, file, economy);
        ItemStackConditionFileParserStrategy itemStackStrategy = new ItemStackConditionFileParserStrategy(configuration, file);
        PermissionConditionFileParserStrategy permissionStrategy = new PermissionConditionFileParserStrategy(configuration, file);
        SkyblockIslandPointsConditionFileParserStrategy pointsStrategy = new SkyblockIslandPointsConditionFileParserStrategy(configuration, file, islanderRepository);
        return Map.of("money", moneyStrategy,
                "item", itemStackStrategy,
                "permission", permissionStrategy,
                "island_points", pointsStrategy);
    }

    private Map<String, AbstractFileParserStrategy<? extends CostCondition>> getCostConditions() {
        MoneyCostConditionFileParserStrategy moneyStrategy = new MoneyCostConditionFileParserStrategy(configuration, file, economy);
        ItemStackCostConditionFileParserStrategy itemStackStrategy = new ItemStackCostConditionFileParserStrategy(configuration, file);
        return Map.of("money", moneyStrategy,
                "item", itemStackStrategy);
    }

    private List<Integer> getLevels() {
        if (!configuration.contains(EXTENDS_PATH)) {
            return new ArrayList<>();
        }
        List<Integer> actualExtends = configuration.getConfigurationSection(EXTENDS_PATH)
                .getKeys(false)
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> result = IntStream.rangeClosed(0, actualExtends.size()).boxed().collect(Collectors.toList());
        Validation.isTrue(actualExtends.equals(result), "Actual extends (" + Arrays.toString(actualExtends.toArray()) + ") levels not fit into pattern (" + Arrays.toString(result.toArray()) + ")");
        return result;
    }

}
