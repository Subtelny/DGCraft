package pl.subtelny.gui.crate.initializer;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.gui.crate.settings.CrateSettings;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

@Component
public class CratesInitializer implements DependencyActivator {

    private final CratesLoaderService crateLoader;

    private final CrateSettings crateSettings;

    @Autowired
    public CratesInitializer(CratesLoaderService crateLoader, CrateSettings crateSettings) {
        this.crateLoader = crateLoader;
        this.crateSettings = crateSettings;
    }

    @Override
    public void activate() {
        initSettings(GUI.plugin);
        initCrates();
    }

    private void initSettings(Plugin plugin) {
        crateSettings.initSettings(plugin);
    }

    private void initCrates() {
        File dir = FileUtil.getFile(GUI.plugin, "guis");
        CrateLoadRequest request = CrateLoadRequest.newBuilder(dir)
                .setPlugin(GUI.plugin)
                .build();
        crateLoader.loadCrates(request);
    }

}
