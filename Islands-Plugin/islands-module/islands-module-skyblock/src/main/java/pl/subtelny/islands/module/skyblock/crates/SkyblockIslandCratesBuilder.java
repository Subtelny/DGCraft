package pl.subtelny.islands.module.skyblock.crates;

import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.islands.api.repository.IslandConfigurationRepository;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;

public class SkyblockIslandCratesBuilder {

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final ItemCrateLoader itemCrateLoader;

    private final IslandConfigurationRepository islandConfigurationRepository;

    private SkyblockIslandModule module;

    public SkyblockIslandCratesBuilder(CrateService crateService,
                                       CratePrototypeLoader cratePrototypeLoader,
                                       ItemCrateLoader itemCrateLoader,
                                       IslandConfigurationRepository islandConfigurationRepository) {
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.itemCrateLoader = itemCrateLoader;
        this.islandConfigurationRepository = islandConfigurationRepository;
    }

    public SkyblockIslandCratesBuilder module(SkyblockIslandModule module) {
        this.module = module;
        return this;
    }

    public SkyblockIslandCratesComponent build() {
        return new SkyblockIslandCratesComponent(module, crateService, cratePrototypeLoader, itemCrateLoader, islandConfigurationRepository);
    }

}
