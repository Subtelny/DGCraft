package pl.subtelny.crate.prototype;

import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeCreator;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;

import java.io.File;
import java.util.List;

public class CratePrototypeFileParserStrategy extends AbstractFileParserStrategy<CratePrototype> {

    private final List<CratePrototypeCreator> creators;

    public CratePrototypeFileParserStrategy(File file, List<CratePrototypeCreator> creators) {
        super(file);
        this.creators = creators;
    }

    @Override
    public CratePrototype load(String path) {
        CrateType crateType = getCrateType();
        return creators.stream()
                .filter(cratePrototypeCreator -> cratePrototypeCreator.getCrateType().equals(crateType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found CratePrototypeCreator for type " + crateType.getValue() + " - " + file.getName()))
                .create(file, configuration);
    }

    @Override
    public Saveable set(String path, CratePrototype value) {
        return null;
    }

    private CrateType getCrateType() {
        return new CrateType(configuration.getString("configuration.type"));
    }

}
