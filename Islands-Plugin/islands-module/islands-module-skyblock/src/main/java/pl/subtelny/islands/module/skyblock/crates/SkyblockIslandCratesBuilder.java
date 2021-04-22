package pl.subtelny.islands.module.skyblock.crates;

import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.islands.module.crates.IslandCrateCreatorStrategy;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;

import java.util.List;

public class SkyblockIslandCratesBuilder {

    private final List<IslandCrateCreatorStrategy<CratePrototype>> crateCreatorStrategies;

    private final CrateService crateService;

    private SkyblockIslandModule module;

    public SkyblockIslandCratesBuilder(List<IslandCrateCreatorStrategy<CratePrototype>> crateCreatorStrategies, CrateService crateService) {
        this.crateCreatorStrategies = crateCreatorStrategies;
        this.crateService = crateService;
    }

    public SkyblockIslandCratesBuilder module(SkyblockIslandModule module) {
        this.module = module;
        return this;
    }

    public SkyblockIslandCrates build() {
        return new SkyblockIslandCrates(crateCreatorStrategies, module, crateService);
    }

}
