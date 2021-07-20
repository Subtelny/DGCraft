package pl.subtelny.crate;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.*;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.creator.CrateCreator;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.creator.DefaultCrateCreators;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class CrateServiceImpl implements CrateService {

    private final CrateStorage crateStorage = new CrateStorage();

    @Override
    public <T extends CratePrototype> Crate createCrate(CrateCreateRequest<T> request) {
        CrateCreator<T> crateCreator = DefaultCrateCreators.getCrateCreator(request.getCratePrototype().getClass());
        return crateCreator.createCrate(request);
    }

    @Override
    public Crate getCrate(CrateId crateId) {
        return getCrate(crateId, CrateData.empty());
    }

    @Override
    public Crate getCrate(CrateId crateId, CrateData data) {
        return crateStorage.findSharedCrate(crateId)
                .orElseGet(() -> getCrateFromPrototype(crateId, data));
    }

    private Crate getCrateFromPrototype(CrateId crateId, CrateData data) {
        CratePrototype prototype = crateStorage.findCratePrototype(crateId)
                .orElseThrow(() -> ValidationException.of("crateService.crate_not_found", crateId.getInternal()));

        CrateCreateRequest<CratePrototype> request = CrateCreateRequest.builder(prototype)
                .crateData(data)
                .build();
        Crate crate = createCrate(request);
        if (prototype.isShared()) {
            crateStorage.addSharedCrate(crate);
        }
        return crate;
    }

}
