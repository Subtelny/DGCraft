package pl.subtelny.crate.api.prototype;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.io.File;

public interface CratePrototypeCreator {

    CratePrototype create(File file, YamlConfiguration config, String path);

    CrateType getCrateType();

    default CratePrototype create(File file, YamlConfiguration config) {
        return create(file, config, "");
    }

}
