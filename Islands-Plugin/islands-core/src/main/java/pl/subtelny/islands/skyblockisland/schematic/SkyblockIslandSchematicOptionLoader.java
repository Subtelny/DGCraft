package pl.subtelny.islands.skyblockisland.schematic;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.condition.itemstack.ItemStackConditionFileParserStrategy;
import pl.subtelny.utilities.condition.itemstack.ItemStackCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyConditionFileParserStrategy;
import pl.subtelny.utilities.condition.money.MoneyCostConditionFileParserStrategy;
import pl.subtelny.utilities.condition.permission.PermissionConditionFileParserStrategy;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.subtelny.utilities.ConfigUtil.getSectionKeys;

public class SkyblockIslandSchematicOptionLoader {

    private final File file;

    private final YamlConfiguration configuration;

    private final Economy economy;

    public SkyblockIslandSchematicOptionLoader(File file, Economy economy) {
        this.file = file;
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.economy = economy;
    }

    public Map<String, SkyblockIslandSchematicOption> loadOptions() {
        String path = "schematics";
        Set<String> schematicKeys = getSectionKeys(configuration, path)
                .orElseThrow(() -> ValidationException.of("schematic.loader.not_found_any_schematic"));

        Map<String, SkyblockIslandSchematicOption> options = schematicKeys.stream()
                .collect(Collectors.toMap(schematic -> schematic, schematic -> loadOption(path + "." + schematic)));
        validateOptions(options);
        return options;
    }

    private void validateOptions(Map<String, SkyblockIslandSchematicOption> loadedOptions) {
        long defaultSchematics = loadedOptions.values().stream()
                .filter(SkyblockIslandSchematicOption::isDefaultSchematic)
                .count();
        Validation.isTrue(defaultSchematics == 1, "schematic.loader.more_than_one_default_schematic");
    }

    private SkyblockIslandSchematicOption loadOption(String path) {
        Map<String, AbstractFileParserStrategy<? extends Condition>> conditions = getConditions();
        Map<String, AbstractFileParserStrategy<? extends CostCondition>> costConditions = getCostConditions();
        return new SkyblockIslandSchematicOptionFileParserStrategy(configuration, file, conditions, costConditions).load(path);
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

}
