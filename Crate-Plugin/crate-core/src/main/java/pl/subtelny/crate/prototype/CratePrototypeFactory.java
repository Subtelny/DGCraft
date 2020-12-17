package pl.subtelny.crate.prototype;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.utilities.exception.ValidationException;

import java.io.File;
import java.util.List;

@Component
public class CratePrototypeFactory {

    private final List<CratePrototypeCreator> cratePrototypeCreators;

    @Autowired
    public CratePrototypeFactory(List<CratePrototypeCreator> cratePrototypeCreators) {
        this.cratePrototypeCreators = cratePrototypeCreators;
    }

    public CratePrototype createCratePrototype(GetCratePrototypeRequest request) {
        CrateType type = getType(request.getFile());
        CratePrototypeCreator creator = getCreator(type);
        return creator.createPrototype(request);
    }

    private CratePrototypeCreator getCreator(CrateType crateType) {
        return cratePrototypeCreators.stream()
                .filter(cratePrototypeCreator -> cratePrototypeCreator.getType().equals(crateType))
                .findFirst()
                .orElseThrow(() -> ValidationException.of("crate_prototype.creator_not_found", crateType));
    }

    protected CrateType getType(File file) {
        String type = YamlConfiguration.loadConfiguration(file).getString("type");
        return new CrateType(type);
    }

}
