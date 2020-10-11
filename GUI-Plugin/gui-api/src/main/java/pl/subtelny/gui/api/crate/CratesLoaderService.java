package pl.subtelny.gui.api.crate;

import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.api.crate.model.CrateId;

import java.io.File;

public interface CratesLoaderService {

    void unloadAllCrates(Plugin plugin);

    void unloadCrate(CrateId crateId);

    void loadCrate(Plugin plugin, File file);

    void loadAllCratesFromDir(Plugin plugin, File dir);

}
