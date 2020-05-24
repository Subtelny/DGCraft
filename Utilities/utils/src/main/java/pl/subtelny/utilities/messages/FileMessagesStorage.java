package pl.subtelny.utilities.messages;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FileMessagesStorage implements MessagesStorage {

    private final String MESSAGES_PATH;

    private Map<String, String> messagesCache = Maps.newHashMap();

    private File file;

    public FileMessagesStorage(String messages_path) {
        MESSAGES_PATH = messages_path;
    }

    public void initialize(File file) {
        Validate.isTrue(file != null, "Message's file already initialized");
        this.file = file;
        loadMessages();
    }

    public void reloadMessages() {
        Validate.isTrue(file != null, "File not initialized yet");
        loadMessages();
    }

    private void loadMessages() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection = config.getConfigurationSection(MESSAGES_PATH);
        if (configurationSection != null) {
            messagesCache = configurationSection
                    .getKeys(false).stream()
                    .collect(mapMessages(config));
        }
    }

    private Collector<String, ?, Map<String, String>> mapMessages(YamlConfiguration config) {
        return Collectors.toMap(
                key -> key,
                key -> getMessageFromConfig(config, MESSAGES_PATH + "." + key)
        );
    }

    private String getMessageFromConfig(YamlConfiguration config, String path) {
        return config.getString(path);
    }

    @Override
    public String get(String path) {
        return Optional.ofNullable(messagesCache.get(path)).orElse(path);
    }

}
