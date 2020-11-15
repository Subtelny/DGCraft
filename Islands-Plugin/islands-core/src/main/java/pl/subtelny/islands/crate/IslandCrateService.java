
package pl.subtelny.islands.crate;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

@Component
public class IslandCrateService implements DependencyActivator {

    private final IslandModules islandModules;

    private final CratesLoaderService cratesLoaderService;

    @Autowired
    public IslandCrateService(IslandModules islandModules, CratesLoaderService cratesLoaderService) {
        this.islandModules = islandModules;
        this.cratesLoaderService = cratesLoaderService;
    }

    public void reloadGuis() {
        cratesLoaderService.unloadAllCrates(Islands.plugin);
        islandModules.getIslandModules()
                .forEach(islandIslandModule -> islandIslandModule.getIslandCrates().reloadCrates());
        loadCrates();
    }

    @Override
    public void activate() {
        loadCrates();
    }

    private void loadCrates() {
        File crates = FileUtil.copyFile(Islands.plugin, "crates");
        CrateLoadRequest request = CrateLoadRequest.newBuilder(crates)
                .setPlugin(Islands.plugin)
                .build();
        cratesLoaderService.loadCrates(request);
    }
}
