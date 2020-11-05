package pl.subtelny.islands.island.skyblockisland.module;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.*;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.membership.IslandMembershipCommandService;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModuleCreator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkyblockIslandModuleCreator implements IslandModuleCreator<SkyblockIsland> {

    private static final String MODULE_TYPE = "SKYBLOCK";

    private final EconomyProvider economyProvider;

    private final ConnectionProvider connectionProvider;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandMembershipQueryService islandMembershipQueryService;

    private final IslandMembershipCommandService islandMembershipCommandService;

    private final IslandMessages islandMessages;

    @Autowired
    public SkyblockIslandModuleCreator(EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandMemberQueryService islandMemberQueryService,
                                       IslandMembershipQueryService islandMembershipQueryService,
                                       IslandMembershipCommandService islandMembershipCommandService,
                                       IslandMessages islandMessages) {
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandMembershipQueryService = islandMembershipQueryService;
        this.islandMembershipCommandService = islandMembershipCommandService;
        this.islandMessages = islandMessages;
    }

    @Override
    public IslandModule<SkyblockIsland> createModule(File moduleDir) {
        SkyblockIslandConfiguration configuration = createConfiguration(moduleDir);
        ConfigReloadable configReloadable = new ConfigReloadable(configuration, moduleDir.getPath());
        configReloadable.reloadConfiguration();
        return new SkyblockIslandModule(connectionProvider,
                new IslandType(moduleDir.getName()),
                configReloadable,
                islandMemberQueryService,
                islandMembershipQueryService,
                islandMembershipCommandService,
                islandMessages);
    }

    @Override
    public String getModuleType() {
        return MODULE_TYPE;
    }

    private SkyblockIslandConfiguration createConfiguration(File moduleDir) {
        File file = new File(moduleDir.getPath(), "configuration.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        String worldName = new ObjectFileParserStrategy<String>(configuration, file).load("configuration.world");
        int defaultSize = new ObjectFileParserStrategy<Integer>(configuration, file).load("configuration.default-size");
        int spaceBetweenIslands = new ObjectFileParserStrategy<Integer>(configuration, file).load("configuration.space-between-islands");
        String schematicFileName = new ObjectFileParserStrategy<String>(configuration, file).load("configuration.schematic-file-name");

        Economy economy = economyProvider.getEconomy();
        IslandExtendConfiguration extendConfiguration = createExtendConfiguration(configuration, file, economy);
        return new SkyblockIslandConfiguration(worldName, extendConfiguration, defaultSize, spaceBetweenIslands, schematicFileName);
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

    private class ConfigReloadable implements ConfigurationReloadable<SkyblockIslandConfiguration> {

        private SkyblockIslandConfiguration configuration;

        private final String modulePath;

        public ConfigReloadable(SkyblockIslandConfiguration configuration, String modulePath) {
            this.configuration = configuration;
            this.modulePath = modulePath;
        }

        @Override
        public SkyblockIslandConfiguration get() {
            return configuration;
        }

        @Override
        public void reloadConfiguration() {
            File moduleDir = new File(modulePath);
            this.configuration = createConfiguration(moduleDir);
        }

    }

}
