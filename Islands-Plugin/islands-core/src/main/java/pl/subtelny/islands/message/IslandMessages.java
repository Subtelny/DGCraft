package pl.subtelny.islands.message;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.messages.FileMessagesStorage;

@Component
public class IslandMessages extends FileMessagesStorage {

    private final static String MESSAGES_PATH = "messages";

    private final static String MESSAGES_FILE_NAME = "messages.yml";

    public IslandMessages() {
        super(MESSAGES_PATH);
    }
}
