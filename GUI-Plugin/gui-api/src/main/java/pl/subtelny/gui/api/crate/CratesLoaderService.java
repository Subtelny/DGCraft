package pl.subtelny.gui.api.crate;

import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.api.crate.model.CrateId;

public interface CratesLoaderService {

    void unloadAllCrates(Plugin plugin);

    void unloadCrate(CrateId crateId);

    void loadCrates(CrateLoadRequest request);

}
