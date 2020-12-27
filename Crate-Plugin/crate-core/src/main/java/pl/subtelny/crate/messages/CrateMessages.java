package pl.subtelny.crate.messages;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.crate.Crate;
import pl.subtelny.utilities.file.FileUtil;
import pl.subtelny.utilities.messages.FileMessagesStorage;
import pl.subtelny.utilities.messages.Messages;

import java.io.File;

@Component
public class CrateMessages extends Messages implements DependencyActivator {

    private static CrateMessages INSTANCE;

    private final static String MESSAGES_PATH = "messages";

    private final static String MESSAGES_FILE_NAME = "messages.yml";

    private final FileMessagesStorage fileMessagesStorage = new FileMessagesStorage(MESSAGES_PATH);

    @Autowired
    public CrateMessages() {
        INSTANCE = this;
    }

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
        initMessages(Crate.plugin);
    }

    public void initMessages(Plugin plugin) {
        File file = FileUtil.copyFile(plugin, MESSAGES_FILE_NAME);
        fileMessagesStorage.initialize(file);
    }

    public static Messages get() {
        return INSTANCE;
    }
}
