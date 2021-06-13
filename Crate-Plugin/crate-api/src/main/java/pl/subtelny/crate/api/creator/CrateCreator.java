package pl.subtelny.crate.api.creator;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;

public interface CrateCreator<T extends CratePrototype> {

    Crate createCrate(CrateCreateRequest<T> request);

}
