package pl.subtelny.crate.cqrs;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.factory.CrateFactory;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.crate.api.query.request.GetPredefinedCrateRequest;
import pl.subtelny.crate.model.crate.global.GlobalCrate;
import pl.subtelny.crate.prototype.CratePrototypeFactory;
import pl.subtelny.crate.repository.CrateRepository;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class CrateQueryServiceImpl implements CrateQueryService {

    private final CrateFactory crateFactory;

    private final CratePrototypeFactory cratePrototypeFactory;

    private final CrateRepository crateRepository;

    @Autowired
    public CrateQueryServiceImpl(CrateFactory crateFactory, CratePrototypeFactory cratePrototypeFactory, CrateRepository crateRepository) {
        this.crateFactory = crateFactory;
        this.cratePrototypeFactory = cratePrototypeFactory;
        this.crateRepository = crateRepository;
    }

    @Override
    public Crate getCrate(GetPredefinedCrateRequest request) {
        return crateFactory.prepareCrate(request.getCratePrototype(), request.getData());
    }

    @Override
    public Crate getCrate(GetCrateRequest request) {
        Optional<Crate> globalCrateOpt = crateRepository.findGlobalCrate(request.getCrateId());
        if (globalCrateOpt.isPresent()) {
            return globalCrateOpt.get();
        }

        CratePrototype cratePrototype = getCratePrototype(request.getCrateId());
        Crate crate = crateFactory.prepareCrate(cratePrototype, request.getData());
        if (cratePrototype.getCrateType().equals(GlobalCrate.GLOBAL_TYPE)) {
            crateRepository.addGlobalCrate(crate);
        }
        return crate;
    }

    @Override
    public CratePrototype getCratePrototype(GetCratePrototypeRequest request) {
        return cratePrototypeFactory.createCratePrototype(request);
    }

    @Override
    public CratePrototype getCratePrototype(CrateId crateId) {
        return findCratePrototype(crateId)
                .orElseThrow(() -> ValidationException.of("crate.not_found", crateId));
    }

    @Override
    public Optional<CratePrototype> findCratePrototype(CrateId crateId) {
        return crateRepository.findCratePrototype(crateId);
    }

}
