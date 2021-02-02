package pl.subtelny.islands.island.crate;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class IslandCratePrototypeFactory {

    private final List<IslandCratePrototypeCreator> cratePrototypeCreators;

    @Autowired
    public IslandCratePrototypeFactory(List<IslandCratePrototypeCreator> cratePrototypeCreators) {
        this.cratePrototypeCreators = cratePrototypeCreators;
    }

    public Optional<CratePrototype> createCratePrototype(GetCratePrototypeRequest request) {
        CrateType type = getType(request.getFile());
        return findCreator(type)
                .map(islandCratePrototypeCreator -> islandCratePrototypeCreator.createPrototype(request));
    }

    private Optional<IslandCratePrototypeCreator> findCreator(CrateType crateType) {
        return cratePrototypeCreators.stream()
                .filter(cratePrototypeCreator -> cratePrototypeCreator.getType().equals(crateType))
                .findFirst();
    }

    protected CrateType getType(File file) {
        String type = YamlConfiguration.loadConfiguration(file).getString("configuration.type");
        return new CrateType(type);
    }

}
