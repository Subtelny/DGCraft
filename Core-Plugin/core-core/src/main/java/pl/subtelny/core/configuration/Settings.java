package pl.subtelny.core.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.FileUtil;

import java.io.File;

public class Settings {

    private static final String CONFIG_FILE_NAME = "config.yml";

    public static String DB_DRIVER = "org.postgresql.Driver";

    public static String DB_TYPE = "postgresql";

    public static String DB_HOST = "localhost";

    public static String DB_PORT = "5432";

    public static String DB_BASE = "dgcraft";

    public static String DB_OPTIONS = "?serverTimezone=UTC&autoReconnect=true&useSSL=false";

    public static String DB_USER = "postgres";

    public static String DB_PASSWORD = "admin";

    private final File file;

    public Settings(Plugin plugin) {
        file = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
    }

    public void initializeFields() {
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
