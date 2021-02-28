package pl.subtelny.islands.island.crate;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.islands.island.Island;

import java.util.Map;

public interface IslandCrateCreator<T extends CratePrototype> {

    Crate create(T prototype, Map<String, String> data, Island island);

    CrateType getType();

}