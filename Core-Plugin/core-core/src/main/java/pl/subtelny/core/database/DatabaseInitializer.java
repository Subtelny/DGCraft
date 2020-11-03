package pl.subtelny.core.database;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.SYSTEM)
public class DatabaseInitializer implements DependencyActivator {

    private static final String CONFIG_FILE_NAME = "database.yml";

    private final CoreDatabaseConnection coreDatabaseConnection;

    @Autowired
    public DatabaseInitializer(CoreDatabaseConnection coreDatabaseConnection) {
        this.coreDatabaseConnection = coreDatabaseConnection;
    }

    @Override
    public void activate(Plugin plugin) {
        File file = FileUtil.copyFile(plugin, CONFIG_FILE_NAME);
        DatabaseConfiguration databaseConfiguration = loadConfiguration(file);
        coreDatabaseConnection.setupDatabase(databaseConfiguration);
    }

    private DatabaseConfiguration loadConfiguration(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String dbDriver = config.getString("database.driver");
        String dbType = config.getString("database.type");
        String dbHost = config.getString("database.host");
        String dbPort = config.getString("database.port");
        String dbBase = config.getString("database.base");
        String dbOptions = config.getString("database.options");
        String dbUser = config.getString("database.user");
        String dbPassword = config.getString("database.password");
        return new DatabaseConfiguration(dbType, dbHost, dbPort, dbBase, dbOptions, dbDriver, dbUser, dbPassword);
    }

}
