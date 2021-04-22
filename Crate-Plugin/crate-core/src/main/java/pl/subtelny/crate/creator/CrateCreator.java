package pl.subtelny.crate.creator;

import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateCreateRequest;
import pl.subtelny.crate.prototype.CratePrototype;

public interface CrateCreator<T extends CratePrototype> {

    Crate createCrate(CrateCreateRequest<T> request);

    Class<T> getClazz();

}
