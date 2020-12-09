package pl.subtelny.crate.service;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.factory.CrateFactory;
import pl.subtelny.crate.model.Crate;
import pl.subtelny.crate.model.CratePrototype;
import pl.subtelny.crate.repository.CrateRepository;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class CrateQueryService {

    private final CrateFactory crateFactory;

    private final CrateRepository crateRepository;

    @Autowired
    public CrateQueryService(CrateFactory crateFactory, CrateRepository crateRepository) {
        this.crateFactory = crateFactory;
        this.crateRepository = crateRepository;
    }

    private Crate getCrate(GetCrateRequest request) {
        Optional<Crate> globalCrateOpt = crateRepository.findGlobalCrate(request.getCrateId());
        if (globalCrateOpt.isPresent()) {
            return globalCrateOpt.get();
        }

        CratePrototype cratePrototype = crateRepository.findCratePrototype(request.getCrateId())
                .orElseThrow(() -> ValidationException.of("crate.not_found", request.getCrateId()));

        return crateFactory.prepareCrate(cratePrototype, request.getData());
    }

}
