package pl.subtelny.core.configuration;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;

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
    public void activate(ComponentPlugin componentPlugin) {
        messages.initMessages(componentPlugin);
        settings.initSettings(componentPlugin);
        locations.initLocations(componentPlugin);
    }
}
