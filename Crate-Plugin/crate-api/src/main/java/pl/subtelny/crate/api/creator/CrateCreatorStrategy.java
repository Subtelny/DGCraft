package pl.subtelny.crate.api.creator;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;

public interface CrateCreatorStrategy<T extends CratePrototype> {

    Crate create(T request);

    CrateType getType();

}
