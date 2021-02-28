package pl.subtelny.islands.island.crate;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.islands.island.skyblockisland.crate.GetIslandCrateRequest;

import java.util.List;
import java.util.Optional;

@Component
public class IslandCrateQueryService {

    private final IslandCratePrototypeFactory cratePrototypeFactory;

    private final CrateQueryService crateQueryService;

    private final List<IslandCrateCreator<CratePrototype>> crateCreators;

    @Autowired
    public IslandCrateQueryService(IslandCratePrototypeFactory cratePrototypeFactory,
                                   CrateQueryService crateQueryService,
                                   List<IslandCrateCreator<CratePrototype>> crateCreators) {
        this.cratePrototypeFactory = cratePrototypeFactory;
        this.crateQueryService = crateQueryService;
        this.crateCreators = crateCreators;
    }

    public CratePrototype getCratePrototype(GetCratePrototypeRequest request) {
        return cratePrototypeFactory.createCratePrototype(request)
                .orElseGet(() -> crateQueryService.constructCratePrototype(request));
    }

    public Crate getCrate(GetIslandCrateRequest request) {
        CratePrototype cratePrototype = crateQueryService.getCratePrototype(request.getCrateId());
        Optional<IslandCrateCreator<CratePrototype>> islandCrateCreator = findIslandCrateCreator(cratePrototype.getCrateType());
        return islandCrateCreator
                .map(crateCreator -> crateCreator.create(cratePrototype, request.getData(), request.getIsland()))
                .orElseGet(() -> crateQueryService.getCrate(request));
    }

    private Optional<IslandCrateCreator<CratePrototype>> findIslandCrateCreator(CrateType crateType) {
        return crateCreators.stream()
                .filter(cratePrototypeIslandCrateCreator -> cratePrototypeIslandCrateCreator.getType().equals(crateType))
                .findFirst();
    }

}
