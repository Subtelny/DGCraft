package pl.subtelny.islands.module.crates.type.search.creator;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.islands.module.crates.IslandCrateCreatorStrategy;
import pl.subtelny.islands.module.crates.type.search.SearchCratePrototype;
import pl.subtelny.islands.island.Island;

@Component
public class SearchCrateCreatorStrategy implements IslandCrateCreatorStrategy<SearchCratePrototype> {

    @Override
    public Crate create(SearchCratePrototype cratePrototype, Island island) {
        return null;
    }

    @Override
    public CrateType getType() {
        return SearchCratePrototype.TYPE;
    }
}
