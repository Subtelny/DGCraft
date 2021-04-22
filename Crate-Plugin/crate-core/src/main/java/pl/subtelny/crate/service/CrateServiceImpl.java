package pl.subtelny.crate.service;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.crate.api.service.InitializeCrateRequest;
import pl.subtelny.crate.creator.CrateCreatorFactory;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.crate.storage.CrateStorage;
import pl.subtelny.crate.type.global.GlobalCratePrototype;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;

@Component
public class CrateServiceImpl implements CrateService {

    private final CrateStorage storage;

    private final CrateCreatorFactory crateCreatorFactory;

    private final CrateInitializer crateInitializer;

    @Autowired
    public CrateServiceImpl(CrateStorage storage,
                            CrateCreatorFactory crateCreatorFactory,
                            CrateInitializer crateInitializer) {
        this.storage = storage;
        this.crateCreatorFactory = crateCreatorFactory;
        this.crateInitializer = crateInitializer;
    }

    @Override
    public Crate getCrate(CrateKey crateKey) {
        return storage.findGlobalCrate(crateKey)
                .orElseGet(() -> createCrateBasedOnCrateKey(crateKey));
    }

    @Override
    public Crate getCrate(CratePrototype cratePrototype) {
        return createCrateBasedOnPrototype(cratePrototype);
    }

    @Override
    public CratePrototype getCratePrototype(CrateKey crateKey) {
        return storage.findCratePrototype(crateKey)
                .orElseThrow(() -> ValidationException.of("crate.not_found", crateKey.getIdentity()));
    }

    @Override
    public CrateKey initializeCrate(InitializeCrateRequest request) {
        return crateInitializer.initializeFromFile(request);
    }

    @Override
    public void unitializeCrates(List<CrateKey> crateKeys) {
        crateInitializer.uninitialize(crateKeys);
    }

    private Crate createCrateBasedOnCrateKey(CrateKey crateKey) {
        CratePrototype cratePrototype = storage.findCratePrototype(crateKey)
                .orElseThrow(() -> ValidationException.of("crate.not_found", crateKey.getIdentity()));
        Crate crate = createCrateBasedOnPrototype(cratePrototype);
        if (GlobalCratePrototype.TYPE.equals(cratePrototype.getCrateType())) {
            storage.addGlobalCrate(crate);
        }
        return crate;
    }

    private Crate createCrateBasedOnPrototype(CratePrototype prototype) {
        CrateType type = prototype.getCrateType();
        CrateCreatorStrategy<CratePrototype> strategy = crateCreatorFactory.getStrategy(type);
        return strategy.create(prototype);
    }

}
