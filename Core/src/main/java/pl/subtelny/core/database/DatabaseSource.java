package pl.subtelny.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import pl.subtelny.beans.Component;
import pl.subtelny.core.configuration.Settings;

import javax.sql.DataSource;

@Component
public class DatabaseSource {

    private final DataSource dataSource;

    private final Configuration configuration;

    public DatabaseSource() {
        dataSource = initializeDataSource();
        configuration = initializeConfiguration();
    }

    private Configuration initializeConfiguration() {
        return new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.POSTGRES);
    }

    private DataSource initializeDataSource() {
        HikariConfig config = new HikariConfig();
        String jdbcUrlBuilder = "jdbc:" +
                Settings.DB_TYPE +
                "://" +
                Settings.DB_HOST +
                ":" +
                Settings.DB_PORT +
                "/" +
                Settings.DB_BASE +
                Settings.DB_OPTIONS;
        config.setJdbcUrl(jdbcUrlBuilder);
        config.setDriverClassName(Settings.DB_DRIVER);
        config.setUsername(Settings.DB_USER);
        config.setPassword(Settings.DB_PASSWORD);
        config.setAutoCommit(true);
        config.setMaximumPoolSize(25);
        return new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
