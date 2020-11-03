package pl.subtelny.islands.island.skyblockisland.module;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.IslandIdToIslandTypeService;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.*;
import pl.subtelny.islands.island.membership.repository.IslandMembershipLoader;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModuleCreator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.islandmember.IslandMemberQueryService;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkyblockIslandModuleCreator implements IslandModuleCreator<SkyblockIsland> {

    private static final String ISLAND_MODULE_NAME = "SKYBLOCK";

    private final EconomyProvider economyProvider;

    private final ConnectionProvider connectionProvider;

    private final IslandIdToIslandTypeService islandIdToIslandTypeService;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandMembershipLoader islandMembershipLoader;

    @Autowired
    public SkyblockIslandModuleCreator(EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandIdToIslandTypeService islandIdToIslandTypeService,
                                       IslandMemberQueryService islandMemberQueryService,
                                       IslandMembershipLoader islandMembershipLoader) {
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandIdToIslandTypeService = islandIdToIslandTypeService;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandMembershipLoader = islandMembershipLoader;
    }

    @Override
    public IslandModule<SkyblockIsland> createModule() {
        ConfigReloadable configReloadable = new ConfigReloadable();
        configReloadable.reloadConfiguration();
        return new SkyblockIslandModule(connectionProvider,
                new IslandType(ISLAND_MODULE_NAME),
                configReloadable,
                islandIdToIslandTypeService,
                islandMemberQueryService,
                islandMembershipLoader);
    }

    private SkyblockIslandConfiguration createConfiguration() {
        File file = FileUtil.copyFile(Islands.plugin, ISLAND_MODULE_NAME.toLowerCase() + ".yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        String worldName = new ObjectFileParserStrategy<String>(configuration, file).load("configuration.world");
        int defaultSize = new ObjectFileParserStrategy<Integer>(configuration, file).load("configuration.default-size");
        int spaceBetweenIslands = new ObjectFileParserStrategy<Integer>(configuration, file).load("configuration.space-between-islands");

        Economy economy = economyProvider.getEconomy();
        IslandExtendConfiguration extendConfiguration = createExtendConfiguration(configuration, file, economy);
        IslandSchematicConfiguration schematicConfiguration = createSchematicConfiguration(configuration, file, economy);
        return new SkyblockIslandConfiguration(worldName, extendConfiguration, schematicConfiguration, defaultSize, spaceBetweenIslands);
    }

    private IslandExtendConfiguration createExtendConfiguration(YamlConfiguration configuration, File file, Economy economy) {
        String path = "configuration.extends";
        List<IslandExtendLevel> extendLevels = ConfigUtil.getSectionKeys(configuration, path)
                .map(paths -> loadIslandExtendLevels(configuration, file, economy, paths))
                .orElseGet(ArrayList::new);
        return new IslandExtendConfiguration(extendLevels);
    }

    private List<IslandExtendLevel> loadIslandExtendLevels(YamlConfiguration configuration, File file, Economy economy, Set<String> paths) {
        IslandExtendLevelFileParserStrategy strategy = new IslandExtendLevelFileParserStrategy(configuration, file, economy);
        return paths.stream()
                .map(strategy::load)
                .collect(Collectors.toList());
    }

    private IslandSchematicConfiguration createSchematicConfiguration(YamlConfiguration configuration, File file, Economy economy) {
        String path = "configuration.schematics";
        List<IslandSchematicLevel> schematicLevels = ConfigUtil.getSectionKeys(configuration, path)
                .map(paths -> loadIslandSchematicLevel(configuration, file, economy, paths))
                .orElseGet(ArrayList::new);
        return new IslandSchematicConfiguration(schematicLevels);
    }

    private List<IslandSchematicLevel> loadIslandSchematicLevel(YamlConfiguration configuration, File file, Economy economy, Set<String> paths) {
        IslandSchematicLevelFileParserStrategy parser = new IslandSchematicLevelFileParserStrategy(configuration, file, economy);
        return paths.stream()
                .map(parser::load)
                .collect(Collectors.toList());
    }

    private class ConfigReloadable implements ConfigurationReloadable<SkyblockIslandConfiguration> {

        private SkyblockIslandConfiguration configuration;

        @Override
        public SkyblockIslandConfiguration get() {
            return configuration;
        }

        @Override
        public void reloadConfiguration() {
            this.configuration = createConfiguration();
        }

    }

}
