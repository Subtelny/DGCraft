package pl.subtelny.crate.factory;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.CrateType;

import java.util.Map;

interface CrateCreator<T extends CratePrototype> {

    Crate create(T prototype,  Map<String, String> data);

    CrateType getType();

}
