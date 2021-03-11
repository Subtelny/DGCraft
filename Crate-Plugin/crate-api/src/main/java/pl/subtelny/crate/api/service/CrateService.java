package pl.subtelny.crate.api.service;

import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.List;

public interface CrateService {

    Crate getCrate(CrateKey crateKey);

    Crate getCrate(CratePrototype cratePrototype);

    Crate getCrate(CrateCreatorRequest request);

    CratePrototype getCratePrototype(CrateKey crateKey);

    List<CrateKey> initializeCrates(InitializeCratesRequest request);

    void unitializeCrates(List<CrateKey> crateKeys);

}
