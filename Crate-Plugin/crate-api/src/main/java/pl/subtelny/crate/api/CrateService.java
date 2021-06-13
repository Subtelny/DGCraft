package pl.subtelny.crate.api;

import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;

public interface CrateService {

    <T extends CratePrototype> Crate createCrate(CrateCreateRequest<T> request);

    Crate getCrate(CrateId crateId);

    Crate getCrate(CrateId crateId, CrateData data);

}
