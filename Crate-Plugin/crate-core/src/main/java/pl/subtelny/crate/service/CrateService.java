package pl.subtelny.crate.service;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.CrateType;
import pl.subtelny.crate.creator.CrateCreatorFactory;
import pl.subtelny.crate.creator.CrateCreatorRequest;
import pl.subtelny.crate.creator.CrateCreatorStrategy;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.crate.storage.CrateStorage;
import pl.subtelny.crate.type.global.GlobalCrateType;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class CrateService {

    private final CrateStorage storage;

    private final CrateCreatorFactory crateCreatorFactory;

    @Autowired
    public CrateService(CrateStorage storage,
                        CrateCreatorFactory crateCreatorFactory) {
        this.storage = storage;
        this.crateCreatorFactory = crateCreatorFactory;
    }

    public Crate getCrate(CrateKey crateKey) {
        return storage.findGlobalCrate(crateKey)
                .orElseGet(() -> createCrateBasedOnCrateKey(crateKey));
    }

    private Crate createCrateBasedOnCrateKey(CrateKey crateKey) {
        Crate crate = storage.findCratePrototype(crateKey)
                .map(this::createCrateBasedOnPrototype)
                .orElseThrow(() -> ValidationException.of("crate.not_found", crateKey.getIdentity()));
        if (GlobalCrateType.isGlobal(crateKey)) {
            storage.addGlobalCrate(crate);
        }
        return crate;
    }

    private Crate createCrateBasedOnPrototype(CratePrototype prototype) {
        CrateType type = prototype.getCrateKey().getType();
        CrateCreatorStrategy<CrateCreatorRequest> strategy = crateCreatorFactory.getStrategy(type);
        CrateCreatorRequest request = prototype.toCrateCreatorRequest();
        return strategy.create(request);
    }

}
