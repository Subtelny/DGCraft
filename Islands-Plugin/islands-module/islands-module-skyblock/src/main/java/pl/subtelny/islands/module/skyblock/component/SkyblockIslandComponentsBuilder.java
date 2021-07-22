package pl.subtelny.islands.module.skyblock.component;

import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.module.component.*;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.crates.SkyblockIslandCratesComponent;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;

import java.util.Map;

public class SkyblockIslandComponentsBuilder {

    private final TransactionProvider transactionProvider;

    private final ConfirmationService confirmationService;

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final ItemCrateLoader itemCrateLoader;

    private final IslandConfigurationRepository islandConfigurationRepository;

    private final IslandMembershipRepository islandMembershipRepository;

    private final IslandMemberQueryService islandMemberQueryService;

    private final SkyblockIslandOrganizer islandOrganizer;

    private final SkyblockIslandRepository repository;

    private SkyblockIslandModule module;

    public SkyblockIslandComponentsBuilder(TransactionProvider transactionProvider,
                                           SkyblockIslandRepository repository,
                                           SkyblockIslandOrganizer islandOrganizer,
                                           ConfirmationService confirmationService,
                                           CrateService crateService,
                                           CratePrototypeLoader cratePrototypeLoader,
                                           ItemCrateLoader itemCrateLoader,
                                           IslandConfigurationRepository islandConfigurationRepository,
                                           IslandMembershipRepository islandMembershipRepository,
                                           IslandMemberQueryService islandMemberQueryService) {
        this.transactionProvider = transactionProvider;
        this.repository = repository;
        this.islandOrganizer = islandOrganizer;
        this.confirmationService = confirmationService;
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.itemCrateLoader = itemCrateLoader;
        this.islandConfigurationRepository = islandConfigurationRepository;
        this.islandMembershipRepository = islandMembershipRepository;
        this.islandMemberQueryService = islandMemberQueryService;
    }

    public SkyblockIslandComponentsBuilder module(SkyblockIslandModule module) {
        this.module = module;
        return this;
    }

    public Map<Class<? extends IslandComponent>, IslandComponent> build() {
        return Map.of(
                InviteComponent.class, new SkyblockIslandInviteComponent(module, confirmationService, islandMembershipRepository),
                CratesComponent.class, new SkyblockIslandCratesComponent(module, crateService, cratePrototypeLoader, itemCrateLoader, islandConfigurationRepository),
                DeleteComponent.class, new SkyblockIslandDeleteComponent(module, transactionProvider, repository, islandOrganizer, islandMemberQueryService, confirmationService),
                CreateComponent.class, new SkyblockIslandCreateComponent(module, transactionProvider, islandOrganizer, repository)
        );
    }

}
