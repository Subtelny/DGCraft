package pl.subtelny.islands.module.skyblock.component;

import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.api.module.component.InviteComponent;
import pl.subtelny.islands.api.module.component.IslandComponent;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.crates.SkyblockIslandCratesComponent;

import java.util.Map;

public class SkyblockIslandComponentsBuilder {

    private final ConfirmationService confirmationService;

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final ItemCrateLoader itemCrateLoader;

    private final IslandConfigurationRepository islandConfigurationRepository;

    private final IslandMembershipRepository islandMembershipRepository;

    private SkyblockIslandModule module;

    public SkyblockIslandComponentsBuilder(ConfirmationService confirmationService,
                                           CrateService crateService,
                                           CratePrototypeLoader cratePrototypeLoader,
                                           ItemCrateLoader itemCrateLoader,
                                           IslandConfigurationRepository islandConfigurationRepository,
                                           IslandMembershipRepository islandMembershipRepository) {
        this.confirmationService = confirmationService;
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.itemCrateLoader = itemCrateLoader;
        this.islandConfigurationRepository = islandConfigurationRepository;
        this.islandMembershipRepository = islandMembershipRepository;
    }

    public SkyblockIslandComponentsBuilder module(SkyblockIslandModule module) {
        this.module = module;
        return this;
    }

    public Map<Class<? extends IslandComponent>, IslandComponent> build() {
        return Map.of(
                InviteComponent.class, new SkyblockIslandInviteComponent(module, confirmationService, islandMembershipRepository),
                CratesComponent.class, new SkyblockIslandCratesComponent(module, crateService, cratePrototypeLoader, itemCrateLoader, islandConfigurationRepository)
        );
    }

}
