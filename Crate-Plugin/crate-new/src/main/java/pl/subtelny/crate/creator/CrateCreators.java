package pl.subtelny.crate.creator;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.CrateCreateRequest;
import pl.subtelny.crate.CrateData;
import pl.subtelny.crate.creator.CrateCreator;
import pl.subtelny.crate.prototype.CratePrototype;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CrateCreators {

    private final Map<Class<? extends CratePrototype>, CrateCreator<?>> crateCreators;

    @Autowired
    public CrateCreators(List<CrateCreator<?>> crateCreators) {
        this.crateCreators = crateCreators.stream().collect(Collectors.toMap(CrateCreator::getClazz, creator -> creator));
    }

    public <T extends CratePrototype> Crate createCrate(T cratePrototype, CrateData crateData) {
        CrateCreator<T> crateCreator = getCrateCreator(cratePrototype.getClass());
        CrateCreateRequest request = CrateCreateRequest
                .builder(cratePrototype)
                .crateData(crateData)
                .build();
        return crateCreator.createCrate(request);
    }

    private <T extends CratePrototype> CrateCreator<T> getCrateCreator(Class<? extends CratePrototype> clazz) {
        CrateCreator<?> crateCreator = crateCreators.entrySet().stream()
                .filter(entry -> entry.getKey().equals(clazz))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found crateCreator for class " + clazz.getName()));
        return (CrateCreator<T>) crateCreator;
    }

}
