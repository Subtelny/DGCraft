package pl.subtelny.gui.crate;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.gui.crate.settings.CratesLoader;
import pl.subtelny.gui.crate.settings.CrateSettings;

@Component
public class CratesInitializer implements DependencyActivator {

    private final CratesLoader crateLoader;

    private final CrateSettings crateSettings;

    @Autowired
    public CratesInitializer(CratesLoader crateLoader, CrateSettings crateSettings) {
        this.crateLoader = crateLoader;
        this.crateSettings = crateSettings;
    }

    @Override
    public void activate(Plugin plugin) {
        crateSettings.initCrates(plugin, crateLoader);
    }

}
