package pl.subtelny.islands.module.skyblock;

import net.milkbowl.vault.economy.Economy;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.ConfigurationReloadableImpl;
import pl.subtelny.islands.island.configuration.ReloadableConfiguration;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.island.message.IslandMessages;
import pl.subtelny.islands.module.InitiableIslandModule;
import pl.subtelny.islands.island.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.IslandModuleCreator;
import pl.subtelny.islands.module.crates.IslandCrateCreatorStrategy;
import pl.subtelny.islands.module.skyblock.configuration.SkyblockIslandModuleConfiguration;
import pl.subtelny.islands.module.skyblock.crates.SkyblockIslandCratesBuilder;
import pl.subtelny.islands.module.skyblock.creator.SkyblockIslandCreator;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.remover.SkyblockIslandRemover;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;

import java.io.File;
import java.util.List;

@Component
public class SkyblockIslandModuleCreator implements IslandModuleCreator<SkyblockIsland> {

    private static final String MODULE_TYPE = "SKYBLOCK";

    private final EconomyProvider economyProvider;

    private final ConnectionProvider connectionProvider;

    private final IslandMembershipRepository islandMembershipRepository;

    private final IslandConfigurationRepository islandConfigurationRepository;

    private final List<IslandCrateCreatorStrategy<CratePrototype>> crateCreatorStrategies;

    private final CrateService crateService;

    @Autowired
    public SkyblockIslandModuleCreator(EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandMembershipRepository islandMembershipRepository,
                                       IslandConfigurationRepository islandConfigurationRepository,
                                       List<IslandCrateCreatorStrategy<CratePrototype>> crateCreatorStrategies,
                                       CrateService crateService) {
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islandConfigurationRepository = islandConfigurationRepository;
        this.crateCreatorStrategies = crateCreatorStrategies;
        this.crateService = crateService;
    }

    @Override
    public InitiableIslandModule<SkyblockIsland> createModule(File moduleDir) {
        IslandType islandType = new IslandType(moduleDir.getName());
        ReloadableConfiguration<SkyblockIslandModuleConfiguration> configuration = getConfiguration(moduleDir);

        SkyblockIslandOrganizer islandOrganizer = new SkyblockIslandOrganizer(configuration, islandType);
        islandOrganizer.initialize(connectionProvider);

        IslandExtendCalculator extendCalculator = new IslandExtendCalculator(configuration);
        SkyblockIslandRepository repository = getRepository(islandType, extendCalculator);
        return new SkyblockIslandModule(islandType,
                configuration,
                repository,
                getIslandCreator(repository, islandOrganizer),
                getIslandRemover(islandOrganizer, repository),
                getSkyblockIslandCratesBuilder()
        );
    }

    private SkyblockIslandCratesBuilder getSkyblockIslandCratesBuilder() {
        return new SkyblockIslandCratesBuilder(crateCreatorStrategies, crateService);
    }

    private SkyblockIslandRemover getIslandRemover(SkyblockIslandOrganizer islandOrganizer, SkyblockIslandRepository repository) {
        return new SkyblockIslandRemover(repository, islandOrganizer);
    }

    @Override
    public String getModuleType() {
        return MODULE_TYPE;
    }

    private ReloadableConfiguration<SkyblockIslandModuleConfiguration> getConfiguration(File moduleDir) {
        Economy economy = economyProvider.getEconomy();
        SkyblockIslandConfigurationFactory factory = new SkyblockIslandConfigurationFactory(economy);
        return new ConfigurationReloadableImpl<>(() -> factory.createConfiguration(moduleDir));
    }

    private SkyblockIslandRepository getRepository(IslandType islandType, IslandExtendCalculator extendCalculator) {
        return new SkyblockIslandRepository(islandType,
                connectionProvider,
                extendCalculator,
                islandMembershipRepository,
                islandConfigurationRepository);
    }

    private SkyblockIslandCreator getIslandCreator(SkyblockIslandRepository repository,
                                                   SkyblockIslandOrganizer islandOrganizer) {
        return new SkyblockIslandCreator(
                repository,
                islandOrganizer,
                IslandMessages.get());
    }


}
