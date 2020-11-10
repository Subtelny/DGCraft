package pl.subtelny.islands.message;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.islands.Islands;

@Component
public class IslandMessagesInitializer implements DependencyActivator {

    private final IslandMessages islandMessages;

    @Autowired
    public IslandMessagesInitializer(IslandMessages islandMessages) {
        this.islandMessages = islandMessages;
    }

    @Override
    public void activate() {
        islandMessages.initMessages(Islands.plugin);
    }
}
