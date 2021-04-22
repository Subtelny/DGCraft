package pl.subtelny.crate.creator;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.type.DefaultCrateCreatorStrategy;

import java.util.List;

@Component
public class CrateCreatorFactory {

    private final static CrateCreatorStrategy<CratePrototype> DEFAULT_CREATOR = new DefaultCrateCreatorStrategy();

    private final List<CrateCreatorStrategy<CratePrototype>> crateCreatorStrategies;

    @Autowired
    public CrateCreatorFactory(List<CrateCreatorStrategy<CratePrototype>> crateCreatorStrategies) {
        this.crateCreatorStrategies = crateCreatorStrategies;
    }

    public CrateCreatorStrategy<CratePrototype> getStrategy(CrateType crateType) {
        return crateCreatorStrategies.stream()
                .filter(strategy -> strategy.getType().equals(crateType))
                .findFirst()
                .orElse(DEFAULT_CREATOR);
    }

}
