package pl.subtelny.islands.island.skyblockisland.crates;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.islands.island.skyblockisland.crates.search.SkyblockIslandSearchCrateCreator;
import pl.subtelny.islands.island.skyblockisland.crates.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.islands.island.skyblockisland.module.SkyblockIslandModule;

import java.util.Map;

@Component
public class SkyblockIslandCrateQueryService {

    private final SkyblockIslandSearchCrateCreator searchCrateCreator;

    private final CrateQueryService crateQueryService;

    @Autowired
    public SkyblockIslandCrateQueryService(SkyblockIslandSearchCrateCreator searchCrateCreator, CrateQueryService crateQueryService) {
        this.searchCrateCreator = searchCrateCreator;
        this.crateQueryService = crateQueryService;
    }

    public Crate getCrate(GetSkyblockCrateRequest request) {
        return crateQueryService.findCratePrototype(request.getCrateId())
                .filter(this::isSkyblockIslandSearchCrateType)
                .map(cratePrototype -> createSearchCrate(cratePrototype, request.getData(), request.getIslandModule()))
                .orElseGet(() -> crateQueryService.getCrate(request));
    }

    private Crate createSearchCrate(CratePrototype cratePrototype, Map<String, String> data, SkyblockIslandModule islandModule) {
        return searchCrateCreator.create((IslandSearchCratePrototype) cratePrototype, data, islandModule);
    }

    private boolean isSkyblockIslandSearchCrateType(CratePrototype cratePrototype) {
        CrateType crateType = cratePrototype.getCrateType();
        return crateType.equals(IslandSearchCratePrototype.SEARCH_CRATE_TYPE);
    }

}
