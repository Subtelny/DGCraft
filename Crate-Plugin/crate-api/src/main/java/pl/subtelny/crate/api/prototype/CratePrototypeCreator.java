package pl.subtelny.crate.api.prototype;

import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;

public interface CratePrototypeCreator {

    CratePrototype createPrototype(GetCratePrototypeRequest request);

    CrateType getType();

}
