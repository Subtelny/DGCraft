package pl.subtelny.crate.api.creator;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;

public interface CrateCreatorStrategy<T extends CrateCreatorRequest> {

    Crate create(T request);

    CrateType getType();

}
