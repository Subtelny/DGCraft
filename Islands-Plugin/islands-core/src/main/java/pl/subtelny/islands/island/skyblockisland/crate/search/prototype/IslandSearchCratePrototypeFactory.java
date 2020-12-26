package pl.subtelny.islands.island.skyblockisland.crate.search.prototype;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;

@Component
public class IslandSearchCratePrototypeFactory {

    private final IslandSearchCratePrototypeCreator searchCratePrototypeCreator;

    @Autowired
    public IslandSearchCratePrototypeFactory(IslandSearchCratePrototypeCreator searchCratePrototypeCreator) {
        this.searchCratePrototypeCreator = searchCratePrototypeCreator;
    }

    public CratePrototype getCratePrototype(GetCratePrototypeRequest request) {
        return searchCratePrototypeCreator.getStrategy(request)
                .load("");
    }

}
