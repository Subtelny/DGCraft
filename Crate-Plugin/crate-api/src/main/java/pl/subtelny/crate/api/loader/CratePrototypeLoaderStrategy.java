package pl.subtelny.crate.api.loader;

import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;

public interface CratePrototypeLoaderStrategy {

    CratePrototype load(CratePrototypeLoadRequest request);

    CrateType getType();

}
