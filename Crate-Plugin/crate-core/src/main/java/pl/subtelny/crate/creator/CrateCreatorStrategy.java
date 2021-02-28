package pl.subtelny.crate.creator;

import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateType;

public interface CrateCreatorStrategy<T extends CrateCreatorRequest> {

    Crate create(T request);

    CrateType getType();

}
