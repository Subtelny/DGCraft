package pl.subtelny.islands.module.skyblock;

import net.milkbowl.vault.economy.Economy;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.configuration.ConfigurationReloadableImpl;
import pl.subtelny.islands.api.configuration.ReloadableConfiguration;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.InitiableIslandModule;
import pl.subtelny.islands.module.IslandModuleCreator;
import pl.subtelny.islands.module.skyblock.component.SkyblockIslandComponentsBuilder;
import pl.subtelny.islands.module.skyblock.configuration.SkyblockIslandModuleConfiguration;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;

import java.io.File;

@Component
public class SkyblockIslandModuleCreator implements IslandModuleCreator<SkyblockIsland> {

    private static final String MODULE_TYPE = "SKYBLOCK";

    private final TransactionProvider transactionProvider;

    private final EconomyProvider economyProvider;

    private final ConnectionProvider connectionProvider;

    private final IslandMembershipRepository islandMembershipRepository;

    private final IslandConfigurationRepository islandConfigurationRepository;

    private final IslandMemberQueryService islandMemberQueryService;

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final ItemCrateLoader itemCrateLoader;

    private final ConfirmationService confirmationService;

    @Autowired
    public SkyblockIslandModuleCreator(TransactionProvider transactionProvider,
                                       EconomyProvider economyProvider,
                                       ConnectionProvider connectionProvider,
                                       IslandMembershipRepository islandMembershipRepository,
                                       IslandConfigurationRepository islandConfigurationRepository,
                                       IslandMemberQueryService islandMemberQueryService,
                                       CrateService crateService,
                                       CratePrototypeLoader cratePrototypeLoader,
                                       ItemCrateLoader itemCrateLoader,
                                       ConfirmationService confirmationService) {
        this.transactionProvider = transactionProvider;
        this.economyProvider = economyProvider;
        this.connectionProvider = connectionProvider;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islandConfigurationRepository = islandConfigurationRepository;
        this.islandMemberQueryService = islandMemberQueryService;
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.itemCrateLoader = itemCrateLoader;
        this.confirmationService = confirmationService;
    }

    @Override
    public InitiableIslandModule<SkyblockIsland> createModule(File moduleDir) {
        IslandType islandType = new IslandType(moduleDir.getName());
        ReloadableConfiguration<SkyblockIslandModuleConfiguration> configuration = getConfiguration(moduleDir);

        SkyblockIslandOrganizer islandOrganizer = new SkyblockIslandOrganizer(configuration, islandType, connectionProvider);
        islandOrganizer.initialize();

        IslandExtendCalculator extendCalculator = new IslandExtendCalculator(configuration);
        SkyblockIslandRepository repository = getRepository(islandType, extendCalculator);
        return new SkyblockIslandModule(islandType,
                configuration,
                repository,
                getSkyblockIslandComponentsBuilder(repository, islandOrganizer)
        );
    }

    private SkyblockIslandComponentsBuilder getSkyblockIslandComponentsBuilder(SkyblockIslandRepository repository,
                                                                               SkyblockIslandOrganizer islandOrganizer) {
        return new SkyblockIslandComponentsBuilder(transactionProvider,
                repository,
                islandOrganizer,
                confirmationService,
                crateService,
                cratePrototypeLoader,
                itemCrateLoader,
                islandConfigurationRepository,
                islandMembershipRepository,
                islandMemberQueryService);
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


}
