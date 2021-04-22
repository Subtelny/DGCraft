package pl.subtelny.utilities.messages;

import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

public class DefaultMessages extends Messages {

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

    public void initMessages(Plugin plugin) {
        File file = FileUtil.copyFile(plugin, MESSAGES_FILE_NAME);
        fileMessagesStorage.initialize(file);
    }

}
