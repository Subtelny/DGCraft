package pl.subtelny.core.configuration;

import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.FileUtil;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Messages {

    private final static String MESSAGES_PATH = "messages";

    private final static String MESSAGES_FILE_NAME = "messages.yml";

    private static Messages instance;

    private final File file;

    private Map<String, String> messages = Maps.newHashMap();

    public Messages(Plugin plugin) {
        instance = this;
        file = FileUtil.copyFile(plugin, MESSAGES_FILE_NAME);
    }

    public void initMessages() {
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

    private String getMessage(String path) {
        return Optional.ofNullable(messages.get(path)).orElse(path);
    }

    public static void reloadMessages() {
        Messages instance = Messages.instance;
        instance.messages.clear();
        instance.initMessages();
    }

    public static String get(String path) {
        return instance.getMessage(path);
    }

}
