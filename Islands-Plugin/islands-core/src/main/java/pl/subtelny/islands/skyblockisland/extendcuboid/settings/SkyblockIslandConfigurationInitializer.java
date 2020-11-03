package pl.subtelny.islands.skyblockisland.extendcuboid.settings;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOW)
public class SkyblockIslandConfigurationInitializer implements DependencyActivator {

    private final SkyblockIslandSettings skyblockIslandSettings;

    @Autowired
    public SkyblockIslandConfigurationInitializer(SkyblockIslandSettings skyblockIslandSettings) {
        this.skyblockIslandSettings = skyblockIslandSettings;
    }

    @Override
    public void activate(Plugin plugin) {
        skyblockIslandSettings.initConfig();
    }

}
