package pl.subtelny.islands.island.message;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.messages.DefaultMessages;
import pl.subtelny.utilities.messages.Messages;

@Component
public class IslandMessages extends DefaultMessages {

    private static IslandMessages instance;

    @Autowired
    public IslandMessages() {
        instance = this;
    }

    public static IslandMessages get() {
        return instance;
    }
}
