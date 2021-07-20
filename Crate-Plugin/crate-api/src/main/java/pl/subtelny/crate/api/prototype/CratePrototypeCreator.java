package pl.subtelny.crate.api.prototype;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.crate.api.CrateType;

import java.io.File;

public interface CratePrototypeCreator<PROTOTYPE extends CratePrototype> {

    PROTOTYPE create(File file, YamlConfiguration config, String path);

    CrateType getCrateType();

    default CratePrototype create(File file, YamlConfiguration config) {
        return create(file, config, "");
    }

    default CratePrototype create(File file) {
        return create(file, YamlConfiguration.loadConfiguration(file), "");
    }

}
