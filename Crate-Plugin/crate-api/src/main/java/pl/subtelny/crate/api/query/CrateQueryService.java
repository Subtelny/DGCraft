package pl.subtelny.crate.api.query;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.crate.api.query.request.GetPredefinedCrateRequest;

public interface CrateQueryService {

    Crate getCrate(GetPredefinedCrateRequest request);

    Crate getCrate(GetCrateRequest request);

}
