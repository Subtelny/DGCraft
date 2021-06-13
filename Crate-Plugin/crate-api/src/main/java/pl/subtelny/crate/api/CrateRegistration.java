package pl.subtelny.crate.api;

import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Collections;
import java.util.List;

public interface CrateRegistration {

    void unregisterCratePrototype(List<CrateId> crateId);

    default void unregisterCratePrototype(CrateId crateId) {
        unregisterCratePrototype(Collections.singletonList(crateId));
    }

    void registerCratePrototype(CratePrototype cratePrototype);

}
