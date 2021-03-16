package pl.subtelny.islands.island.skyblockisland.module;

import net.milkbowl.vault.economy.Economy;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.configuration.ConfigurationReloadableImpl;
import pl.subtelny.islands.island.configuration.ReloadableConfiguration;
import pl.subtelny.islands.island.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.island.module.IslandModuleCreator;
import pl.subtelny.islands.island.module.islandModuleInitable;
import pl.subtelny.islands.island.repository.IslandConfigurationRepository;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.island.skyblockisland.configuration.SkyblockIslandConfiguration;
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

    private final IslandConfigurationRepository islandConfigurationRepository;

    private final IslandMessages islandMessages;

    private final CrateService crateService;

    @Autowired
    public SkyblockIslandModuleCreator(EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandMembershipRepository islandMembershipRepository,
                                       IslandConfigurationRepository islandConfigurationRepository,
                                       IslandMessages islandMessages,
                                       CrateService crateService) {
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islandConfigurationRepository = islandConfigurationRepository;
        this.islandMessages = islandMessages;
        this.crateService = crateService;
    }

    @Override
    public islandModuleInitable<SkyblockIsland> createModule(File moduleDir) {
        IslandType islandType = new IslandType(moduleDir.getName());
        ReloadableConfiguration<SkyblockIslandConfiguration> configuration = getConfiguration(moduleDir);

        SkyblockIslandOrganizer islandOrganizer = new SkyblockIslandOrganizer(configuration, islandType);
        islandOrganizer.initialize(connectionProvider);

        IslandExtendCalculator extendCalculator = new IslandExtendCalculator(configuration);
        SkyblockIslandRepository repository = getRepository(islandType, extendCalculator);
        return new SkyblockIslandModule(islandType,
                configuration,
                repository,
                getIslandCreator(repository, islandOrganizer),
                getIslandRemover(islandOrganizer, repository),
                crateService
        );
    }

    private SkyblockIslandRemover getIslandRemover(SkyblockIslandOrganizer islandOrganizer, SkyblockIslandRepository repository) {
        return new SkyblockIslandRemover(repository, islandOrganizer);
    }

    @Override
    public String getModuleType() {
        return MODULE_TYPE;
    }

    private ReloadableConfiguration<SkyblockIslandConfiguration> getConfiguration(File moduleDir) {
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
                islandMessages);
    }


}
