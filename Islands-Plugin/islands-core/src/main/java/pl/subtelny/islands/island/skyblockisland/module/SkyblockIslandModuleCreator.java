package pl.subtelny.islands.island.skyblockisland.module;

import net.milkbowl.vault.economy.Economy;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.islands.crates.IslandRewardFileParserStrategyFactory;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.ConfigurationReloadable;
import pl.subtelny.islands.island.configuration.ConfigurationReloadableImpl;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.membership.IslandMembershipCommandService;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModuleCreator;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.crates.SkyblockIslandCratesLoader;
import pl.subtelny.islands.island.skyblockisland.creator.SkyblockIslandCreator;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.message.IslandMessages;

import java.io.File;

@Component
public class SkyblockIslandModuleCreator implements IslandModuleCreator<SkyblockIsland> {

    private static final String MODULE_TYPE = "SKYBLOCK";

    private final EconomyProvider economyProvider;

    private final ConnectionProvider connectionProvider;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandMembershipQueryService islandMembershipQueryService;

    private final IslandMembershipCommandService islandMembershipCommandService;

    private final IslandMessages islandMessages;

    private final CratesLoaderService cratesLoaderService;

    private final IslandRewardFileParserStrategyFactory strategyFactory;

    private final PlayerCrateSessionService playerCrateSessionService;

    @Autowired
    public SkyblockIslandModuleCreator(EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandMemberQueryService islandMemberQueryService,
                                       IslandMembershipQueryService islandMembershipQueryService,
                                       IslandMembershipCommandService islandMembershipCommandService,
                                       IslandMessages islandMessages,
                                       CratesLoaderService cratesLoaderService,
                                       IslandRewardFileParserStrategyFactory strategyFactory,
                                       PlayerCrateSessionService playerCrateSessionService) {
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandMembershipQueryService = islandMembershipQueryService;
        this.islandMembershipCommandService = islandMembershipCommandService;
        this.islandMessages = islandMessages;
        this.cratesLoaderService = cratesLoaderService;
        this.strategyFactory = strategyFactory;
        this.playerCrateSessionService = playerCrateSessionService;
    }

    @Override
    public IslandModule<SkyblockIsland> createModule(File moduleDir) {
        IslandType islandType = new IslandType(moduleDir.getName());
        ConfigurationReloadable<SkyblockIslandConfiguration> configuration = getConfiguration(moduleDir);

        IslandExtendCalculator extendCalculator = new IslandExtendCalculator(configuration);
        SkyblockIslandRepository repository = getRepository(islandType, extendCalculator);
        SkyblockIslandModule skyblockIslandModule = new SkyblockIslandModule(islandType,
                configuration,
                getIslandCrates(moduleDir),
                repository,
                getIslandCreator(repository, islandType, extendCalculator),
                islandMembershipCommandService,
                playerCrateSessionService
        );
        skyblockIslandModule.getIslandCrates().reloadCrates();
        return skyblockIslandModule;
    }

    @Override
    public String getModuleType() {
        return MODULE_TYPE;
    }

    private ConfigurationReloadable<SkyblockIslandConfiguration> getConfiguration(File moduleDir) {
        Economy economy = economyProvider.getEconomy();
        SkyblockIslandConfigurationFactory factory = new SkyblockIslandConfigurationFactory(economy);
        return new ConfigurationReloadableImpl<>(() -> factory.createConfiguration(moduleDir));
    }

    private SkyblockIslandCratesLoader getIslandCrates(File moduleDir) {
        File cratesDir = new File(moduleDir, "crates");
        return new SkyblockIslandCratesLoader(strategyFactory, cratesLoaderService, cratesDir);
    }

    private SkyblockIslandRepository getRepository(IslandType islandType, IslandExtendCalculator extendCalculator) {
        return new SkyblockIslandRepository(islandType,
                connectionProvider,
                islandMemberQueryService,
                islandMembershipQueryService,
                extendCalculator);
    }

    private SkyblockIslandCreator getIslandCreator(SkyblockIslandRepository repository,
                                                   IslandType islandType,
                                                   IslandExtendCalculator extendCalculator) {
        return new SkyblockIslandCreator(connectionProvider,
                repository,
                islandType,
                islandMembershipCommandService,
                extendCalculator,
                islandMessages);
    }


}
