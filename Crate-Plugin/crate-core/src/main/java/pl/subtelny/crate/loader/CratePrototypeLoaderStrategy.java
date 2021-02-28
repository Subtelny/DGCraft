package pl.subtelny.crate.loader;

import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.prototype.CratePrototype;

public interface CratePrototypeLoaderStrategy {

    CratePrototype load(CratePrototypeLoadRequest request);

    CrateType getType();

}
