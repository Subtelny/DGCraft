package pl.subtelny.islands.module.crates;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.islands.island.Island;

public interface IslandCrateCreatorStrategy<T extends CratePrototype> {

    Crate create(T cratePrototype, Island island);

    CrateType getType();

}
