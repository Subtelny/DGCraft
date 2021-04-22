package pl.subtelny.crate.prototype;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.CrateType;

import java.io.File;

public interface CratePrototypeCreator {

    CratePrototype create(File file, YamlConfiguration config, String path);

    CrateType getCrateType();

    default CratePrototype create(File file, YamlConfiguration config) {
        return create(file, config, "");
    }

}
