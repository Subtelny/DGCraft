package pl.subtelny.crate.api.query;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.crate.api.query.request.GetPredefinedCrateRequest;

import java.util.Optional;

public interface CrateQueryService {

    Crate getCrate(GetPredefinedCrateRequest request);

    Crate getCrate(GetCrateRequest request);

    CratePrototype getCratePrototype(GetCratePrototypeRequest request);

    CratePrototype getCratePrototype(CrateId crateId);

    Optional<CratePrototype> findCratePrototype(CrateId crateId);

}
