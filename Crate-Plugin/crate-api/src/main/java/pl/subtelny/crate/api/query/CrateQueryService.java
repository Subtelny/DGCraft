package pl.subtelny.crate.api.query;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.crate.api.query.request.GetPredefinedCrateRequest;

import java.io.File;

public interface CrateQueryService {

    Crate getCrate(GetPredefinedCrateRequest request);

    Crate getCrate(GetCrateRequest request);

    CratePrototype getCratePrototype(File file);

}
