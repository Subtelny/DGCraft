package pl.subtelny.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyInitialized;

@Component
public class ConfigurationInitializer implements DependencyInitialized {

    private final CoreMessages messages;

    private final Settings settings;

    @Autowired
    public ConfigurationInitializer(CoreMessages messages, Settings settings) {
        this.messages = messages;
        this.settings = settings;
    }

    @Override
    public void dependencyInitialized(Plugin plugin) {
        messages.initMessages(plugin);
        settings.initSettings(plugin);
    }
}
