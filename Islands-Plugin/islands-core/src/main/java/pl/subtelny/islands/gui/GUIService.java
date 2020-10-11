
package pl.subtelny.islands.gui;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.islands.Islands;
import pl.subtelny.utilities.FileUtil;

@Component
public class GUIService {

    private final CratesLoaderService cratesLoaderService;

    @Autowired
    public GUIService(CratesLoaderService cratesLoaderService) {
        this.cratesLoaderService = cratesLoaderService;
    }

    public void reloadGuis() {
        Plugin plugin = Islands.plugin;
        cratesLoaderService.unloadAllCrates(plugin);
        cratesLoaderService.loadAllCratesFromDir(plugin, FileUtil.getFile(plugin, "guis"));
    }

}
