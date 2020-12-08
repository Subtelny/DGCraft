package pl.subtelny.islands.message;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.islands.Islands;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.messages.FileMessagesStorage;
import pl.subtelny.utilities.messages.Messages;

import java.io.File;

@Component
public class IslandMessages extends Messages implements DependencyActivator {

    private final static String MESSAGES_PATH = "messages";

    private final static String MESSAGES_FILE_NAME = "messages.yml";

    private final FileMessagesStorage fileMessagesStorage = new FileMessagesStorage(MESSAGES_PATH);

    @Override
    public String getRawMessage(String path) {
        return fileMessagesStorage.get(path);
    }

    @Override
    public void reloadMessages() {
        fileMessagesStorage.reloadMessages();
    }

    @Override
    public void activate() {
        initMessages(Islands.plugin);
    }

    public void initMessages(Plugin plugin) {
        File file = FileUtil.copyFile(plugin, MESSAGES_FILE_NAME);
        fileMessagesStorage.initialize(file);
    }
}
