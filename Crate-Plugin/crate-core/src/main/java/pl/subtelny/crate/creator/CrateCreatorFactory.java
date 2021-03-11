package pl.subtelny.crate.creator;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;

import java.util.List;

@Component
public class CrateCreatorFactory {

    private final List<CrateCreatorStrategy<CrateCreatorRequest>> crateCreatorStrategies;

    @Autowired
    public CrateCreatorFactory(List<CrateCreatorStrategy<CrateCreatorRequest>> crateCreatorStrategies) {
        this.crateCreatorStrategies = crateCreatorStrategies;
    }

    public CrateCreatorStrategy<CrateCreatorRequest> getStrategy(CrateType crateType) {
        return crateCreatorStrategies.stream()
                .filter(strategy -> strategy.getType().equals(crateType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found strategy for type " + crateType));
    }

}
