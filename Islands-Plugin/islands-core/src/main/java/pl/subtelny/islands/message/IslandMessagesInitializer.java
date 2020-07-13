package pl.subtelny.islands.message;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;

@Component
public class IslandMessagesInitializer implements DependencyActivator {

    private final IslandMessages islandMessages;

    @Autowired
    public IslandMessagesInitializer(IslandMessages islandMessages) {
        this.islandMessages = islandMessages;
    }

    @Override
    public void activate(Plugin plugin) {
        islandMessages.initMessages(plugin);
    }
}
