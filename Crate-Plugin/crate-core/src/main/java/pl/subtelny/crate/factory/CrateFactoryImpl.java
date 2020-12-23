package pl.subtelny.crate.factory;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.factory.CrateFactory;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.List;
import java.util.Map;

@Component
public class CrateFactoryImpl implements CrateFactory {

    private final List<CrateCreator<CratePrototype>> crateCreators;

    @Autowired
    public CrateFactoryImpl(List<CrateCreator<CratePrototype>> crateCreators) {
        this.crateCreators = crateCreators;
    }

    @Override
    public Crate prepareCrate(CratePrototype prototype, Map<String, String> data) {
        CrateCreator<CratePrototype> crateCreator = getCrateCreator(prototype);
        return crateCreator.create(prototype, data);
    }

    private CrateCreator<CratePrototype> getCrateCreator(CratePrototype prototype) {
        return crateCreators.stream()
                .filter(crateCreator -> crateCreator.getType().equals(prototype.getCrateType()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found crate creator for type " + prototype.getCrateType()));
    }

}


