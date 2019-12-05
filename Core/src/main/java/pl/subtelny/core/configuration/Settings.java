package pl.subtelny.core.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.utils.FileUtil;

import java.io.File;

public class Settings {

    public static String DB_DRIVER;

    public static String DB_TYPE;

    public static String DB_HOST;

    public static String DB_PORT;

    public static String DB_BASE;

    public static String DB_OPTIONS;

    public static String DB_USER;

    public static String DB_PASSWORD;

    private final File file;

    public Settings(Plugin plugin) {
        file = FileUtil.copyFile(plugin, "config.yml");
        initializeFields();
    }

    private void initializeFields() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        DB_DRIVER = config.getString("database.driver");
        DB_TYPE = config.getString("database.type");
        DB_HOST = config.getString("database.host");
        DB_PORT = config.getString("database.port");
        DB_BASE = config.getString("database.base");
        DB_OPTIONS = config.getString("database.options");
        DB_USER = config.getString("database.user");
        DB_PASSWORD = config.getString("database.password");
    }

}
