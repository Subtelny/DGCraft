package pl.subtelny.islands.island.crate;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.invites.IslandInvitesCrateCreator;
import pl.subtelny.islands.island.crate.invites.prototype.IslandInvitesCratePrototype;
import pl.subtelny.islands.island.crate.search.IslandSearchCrateCreator;
import pl.subtelny.islands.island.crate.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.islands.island.skyblockisland.crate.GetIslandCrateRequest;

import java.util.Map;

@Component
public class IslandCrateQueryService {

    private final IslandSearchCrateCreator searchCrateCreator;

    private final IslandInvitesCrateCreator invitesCrateCreator;

    private final IslandCratePrototypeFactory cratePrototypeFactory;

    private final CrateQueryService crateQueryService;

    @Autowired
    public IslandCrateQueryService(IslandSearchCrateCreator searchCrateCreator,
                                   IslandInvitesCrateCreator invitesCrateCreator,
                                   IslandCratePrototypeFactory cratePrototypeFactory,
                                   CrateQueryService crateQueryService) {
        this.searchCrateCreator = searchCrateCreator;
        this.invitesCrateCreator = invitesCrateCreator;
        this.cratePrototypeFactory = cratePrototypeFactory;
        this.crateQueryService = crateQueryService;
    }

    public CratePrototype getCratePrototype(GetCratePrototypeRequest request) {
        return cratePrototypeFactory.createCratePrototype(request)
                .orElseGet(() -> crateQueryService.getCratePrototype(request));
    }

    public Crate getCrate(GetIslandCrateRequest request) {
        CratePrototype cratePrototype = crateQueryService.getCratePrototype(request.getCrateId());
        if (isIslandSearchCrate(cratePrototype)) {
            return createIslandSearchCrate((IslandSearchCratePrototype) cratePrototype, request.getData());
        }
        if (isIslandInvitesCrate(cratePrototype)) {
            return createIslandInvitesCrate((IslandInvitesCratePrototype) cratePrototype, request.getData(), request.getIsland());
        }
        return crateQueryService.getCrate(request);
    }

    private boolean isIslandSearchCrate(CratePrototype cratePrototype) {
        return IslandSearchCratePrototype.SEARCH_CRATE_TYPE.equals(cratePrototype.getCrateType());
    }

    private boolean isIslandInvitesCrate(CratePrototype cratePrototype) {
        return IslandInvitesCratePrototype.ISLAND_CRATE_TYPE.equals(cratePrototype.getCrateType());
    }

    private Crate createIslandSearchCrate(IslandSearchCratePrototype cratePrototype, Map<String, String> data) {
        return searchCrateCreator.create(cratePrototype, data);
    }

    private Crate createIslandInvitesCrate(IslandInvitesCratePrototype prototype, Map<String, String> data, Island island) {
        return invitesCrateCreator.create(prototype, data, island);
    }

}
