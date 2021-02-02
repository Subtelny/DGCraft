package pl.subtelny.islands.island.skyblockisland.module;

import net.milkbowl.vault.economy.Economy;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.ConfigurationReloadable;
import pl.subtelny.islands.island.configuration.ConfigurationReloadableImpl;
import pl.subtelny.islands.island.crate.IslandCrateQueryService;
import pl.subtelny.islands.island.crate.parser.IslandRewardFileParserStrategyFactory;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.island.module.IslandModuleCreator;
import pl.subtelny.islands.island.module.IslandModuleInitable;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
import pl.subtelny.islands.island.skyblockisland.crate.SkyblockIslandCratesLoader;
import pl.subtelny.islands.island.skyblockisland.creator.SkyblockIslandCreator;
import pl.subtelny.islands.island.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.island.skyblockisland.remover.SkyblockIslandRemover;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandRepository;
import pl.subtelny.islands.message.IslandMessages;

import java.io.File;

@Component
public class SkyblockIslandModuleCreator implements IslandModuleCreator<SkyblockIsland> {

    private static final String MODULE_TYPE = "SKYBLOCK";

    private final EconomyProvider economyProvider;

    private final ConnectionProvider connectionProvider;

    private final IslandMembershipRepository islandMembershipRepository;

    private final IslandMessages islandMessages;

    private final IslandRewardFileParserStrategyFactory strategyFactory;

    private final CrateCommandService crateCommandService;

    private final IslandCrateQueryService islandCrateQueryService;

    @Autowired
    public SkyblockIslandModuleCreator(EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandMembershipRepository islandMembershipRepository,
                                       IslandMessages islandMessages,
                                       IslandRewardFileParserStrategyFactory strategyFactory,
                                       CrateCommandService crateCommandService,
                                       IslandCrateQueryService islandCrateQueryService) {
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islandMessages = islandMessages;
        this.strategyFactory = strategyFactory;
        this.crateCommandService = crateCommandService;
        this.islandCrateQueryService = islandCrateQueryService;
    }

    @Override
    public IslandModuleInitable<SkyblockIsland> createModule(File moduleDir) {
        IslandType islandType = new IslandType(moduleDir.getName());
        ConfigurationReloadable<SkyblockIslandConfiguration> configuration = getConfiguration(moduleDir);

        SkyblockIslandOrganizer islandOrganizer = new SkyblockIslandOrganizer(configuration, islandType);
        islandOrganizer.initialize(connectionProvider);

        IslandExtendCalculator extendCalculator = new IslandExtendCalculator(configuration);
        SkyblockIslandRepository repository = getRepository(islandType, extendCalculator);
        return new SkyblockIslandModule(islandType,
                configuration,
                getIslandCrates(moduleDir),
                repository,
                getIslandCreator(repository, islandOrganizer),
                getIslandRemover(islandOrganizer, repository),
                crateCommandService,
                islandCrateQueryService);
    }

    private SkyblockIslandRemover getIslandRemover(SkyblockIslandOrganizer islandOrganizer, SkyblockIslandRepository repository) {
        return new SkyblockIslandRemover(repository, islandOrganizer);
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
        return new SkyblockIslandCratesLoader(strategyFactory, islandCrateQueryService, crateCommandService, cratesDir);
    }

    private SkyblockIslandRepository getRepository(IslandType islandType, IslandExtendCalculator extendCalculator) {
        return new SkyblockIslandRepository(islandType,
                connectionProvider,
                extendCalculator,
                islandMembershipRepository);
    }

    private SkyblockIslandCreator getIslandCreator(SkyblockIslandRepository repository,
                                                   SkyblockIslandOrganizer islandOrganizer) {
        return new SkyblockIslandCreator(
                repository,
                islandOrganizer,
                islandMessages);
    }


}
