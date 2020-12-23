package pl.subtelny.crate.api.factory;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;

public interface CrateFactory {

    Crate prepareCrate(CratePrototype prototype, Map<String, String> data);

}
