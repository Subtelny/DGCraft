package pl.subtelny.core.configuration;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.commands.api.CommandMessages;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.FileUtil;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public final class Messages implements CommandMessages {

    private final static String MESSAGES_PATH = "messages";

    private final static String MESSAGES_FILE_NAME = "messages.yml";

    private Map<String, String> messages = Maps.newHashMap();

    private File file;

    public void initMessages(Plugin plugin) {
        Validate.isTrue(file == null, "Message's file already initialized");
        file = FileUtil.copyFile(plugin, MESSAGES_FILE_NAME);
        loadMessages();
    }

    public void reloadMessages() {
        Validate.notNull(file, "Messages file're not initialized yet");
        loadMessages();
    }

    private void loadMessages() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection = config.getConfigurationSection(MESSAGES_PATH);
        if (configurationSection != null) {
            messages = configurationSection
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
        return Optional.ofNullable(messages.get(path)).orElse(path);
    }

}
