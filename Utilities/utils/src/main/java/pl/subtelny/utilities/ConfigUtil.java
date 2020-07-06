package pl.subtelny.utilities;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Optional;

public final class ConfigUtil {

    public static Optional<Boolean> getBoolean(YamlConfiguration configuration, String path) {
        if (!configuration.contains(path)) {
            return Optional.empty();
        }
        return Optional.of(configuration.getBoolean(path));
    }

    public static Optional<String> getString(YamlConfiguration configuration, String path) {
        return Optional.ofNullable(configuration.getString(path, null));
    }

    public static Optional<Integer> getInt(YamlConfiguration configuration, String path) {
        if (!configuration.contains(path)) {
            return Optional.empty();
        }
        return Optional.of(configuration.getInt(path));
    }

    public static Optional<List<String>> getStringList(YamlConfiguration configuration, String path) {
        if (configuration.contains(path)) {
            return Optional.empty();
        }
        return Optional.of(configuration.getStringList(path));
    }

}
