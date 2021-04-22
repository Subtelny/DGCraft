package pl.subtelny.crate;

import pl.subtelny.crate.prototype.CratePrototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateStorage {

    private final Map<CrateId, CratePrototype> prototypes = new HashMap<>();

    private final Map<CrateId, Crate> sharedCrates = new HashMap<>();

    public Optional<Crate> findSharedCrate(CrateId crateId) {
        return Optional.ofNullable(sharedCrates.get(crateId));
    }

    public Optional<CratePrototype> findCratePrototype(CrateId crateId) {
        return Optional.ofNullable(prototypes.get(crateId));
    }

    public void addSharedCrate(Crate crate) {
        sharedCrates.put(crate.getCrateId(), crate);
    }

    public void removeSharedCrate(CrateId crateId) {
        sharedCrates.remove(crateId);
    }

    public void addCratePrototype(CratePrototype cratePrototype) {
        prototypes.put(cratePrototype.getCrateId(), cratePrototype);
    }

    public void removeCratePrototype(CrateId crateId) {
        prototypes.remove(crateId);
    }

}
