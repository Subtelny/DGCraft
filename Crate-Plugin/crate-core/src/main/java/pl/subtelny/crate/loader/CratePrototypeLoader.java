package pl.subtelny.crate.loader;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.loader.CratePrototypeLoaderStrategy;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.utilities.file.ObjectFileParserStrategy;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class CratePrototypeLoader {

    private final List<CratePrototypeLoaderStrategy> loaderStrategies;

    @Autowired
    public CratePrototypeLoader(List<CratePrototypeLoaderStrategy> loaderStrategies) {
        this.loaderStrategies = loaderStrategies;
    }

    public CratePrototype load(CratePrototypeLoadRequest request) {
        CratePrototypeLoaderStrategy strategy = findStrategy(request.getFile());
        return strategy.load(request);
    }

    private CratePrototypeLoaderStrategy findStrategy(File file) {
        String rawType = new ObjectFileParserStrategy<String>(file).load("configuration.type");
        return findStrategy(CrateType.of(rawType))
                .orElseThrow(() -> new IllegalStateException("Not found strategy for type " + rawType + ", " + file.getName()));
    }

    private Optional<CratePrototypeLoaderStrategy> findStrategy(CrateType crateType) {
        return loaderStrategies.stream()
                .filter(strategy -> strategy.getType().equals(crateType))
                .findFirst();
    }

}
