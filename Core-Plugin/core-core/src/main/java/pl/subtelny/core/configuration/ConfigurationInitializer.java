package pl.subtelny.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;

@Component
public class ConfigurationInitializer implements DependencyActivator {

    private final CoreMessages messages;

    private final Settings settings;

    private final Locations locations;

    @Autowired
    public ConfigurationInitializer(CoreMessages messages, Settings settings, Locations locations) {
        this.messages = messages;
        this.settings = settings;
        this.locations = locations;
    }

    @Override
    public void activate(Plugin plugin) {
        messages.initMessages(plugin);
        settings.initSettings(plugin);
        locations.initLocations(plugin);
    }
}
