package pl.subtelny.crate.storage;

import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.type.global.GlobalCrateType;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.utilities.Validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateStorage {

    private final Map<CrateKey, Crate> globalCrates = new HashMap<>();

    private final Map<CrateKey, CratePrototype> cratePrototypes = new HashMap<>();

    public void addGlobalCrate(Crate crate) {
        CrateKey key = crate.getKey();
        Validation.isTrue(GlobalCrateType.isGlobal(key), "Cannot add non global crate into global crates list");
        globalCrates.put(key, crate);
    }

    public void removeGlobalCrate(CrateKey crateKey) {
        globalCrates.remove(crateKey);
    }

    public void removeCratePrototype(CrateKey crateKey) {
        cratePrototypes.remove(crateKey);
    }

    public void addCratePrototype(CratePrototype cratePrototype) {
        cratePrototypes.put(cratePrototype.getCrateKey(), cratePrototype);
    }

    public Optional<Crate> findGlobalCrate(CrateKey crateKey) {
        return Optional.ofNullable(globalCrates.get(crateKey));
    }

    public Optional<CratePrototype> findCratePrototype(CrateKey crateKey) {
        return Optional.ofNullable(cratePrototypes.get(crateKey));
    }

}
