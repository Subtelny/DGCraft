package pl.subtelny.crate.service;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.crate.api.service.InitializeCratesRequest;
import pl.subtelny.crate.creator.CrateCreatorFactory;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.initializer.CrateInitializer;
import pl.subtelny.crate.storage.CrateStorage;
import pl.subtelny.crate.api.type.global.GlobalCrateType;
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
    public Crate getCrate(CrateCreatorRequest request) {
        return createCrateBasedOnRequest(request);
    }

    @Override
    public CratePrototype getCratePrototype(CrateKey crateKey) {
        return storage.findCratePrototype(crateKey)
                .orElseThrow(() -> ValidationException.of("crate.not_found", crateKey.getIdentity()));
    }

    @Override
    public List<CrateKey> initializeCrates(InitializeCratesRequest request) {
        return crateInitializer.initializeFromDir(request.getDir(), request.getPlugin(), request.getPrefixKey());
    }

    @Override
    public void unitializeCrates(List<CrateKey> crateKeys) {
        crateInitializer.uninitialize(crateKeys);
    }

    private Crate createCrateBasedOnCrateKey(CrateKey crateKey) {
        CratePrototype cratePrototype = storage.findCratePrototype(crateKey)
                .orElseThrow(() -> ValidationException.of("crate.not_found", crateKey.getIdentity()));
        Crate crate = createCrateBasedOnPrototype(cratePrototype);
        if (GlobalCrateType.isGlobal(cratePrototype.getCrateType())) {
            storage.addGlobalCrate(crate);
        }
        return crate;
    }

    private Crate createCrateBasedOnPrototype(CratePrototype prototype) {
        return createCrateBasedOnRequest(prototype.toCrateCreatorRequest());
    }

    private Crate createCrateBasedOnRequest(CrateCreatorRequest request) {
        CrateType type = request.getCrateType();
        CrateCreatorStrategy<CrateCreatorRequest> strategy = crateCreatorFactory.getStrategy(type);
        return strategy.create(request);
    }

}
