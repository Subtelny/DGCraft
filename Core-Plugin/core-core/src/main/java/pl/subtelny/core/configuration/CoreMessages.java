package pl.subtelny.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.FileUtil;
import pl.subtelny.utilities.messages.FileMessagesStorage;
import pl.subtelny.utilities.messages.Messages;

@Component
public final class CoreMessages extends Messages {

    private final FileMessagesStorage fileMessagesStorage;

    private final static String MESSAGES_PATH = "messages";

    private final static String MESSAGES_FILE_NAME = "messages.yml";

    public CoreMessages() {
        super(new FileMessagesStorage(MESSAGES_PATH));
    }

    public void initMessages(Plugin plugin) {
        initialize(FileUtil.copyFile(plugin, MESSAGES_FILE_NAME));
    }

}
