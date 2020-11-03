package pl.subtelny.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;

import javax.sql.DataSource;
import java.util.concurrent.Executor;

@Component
public class CoreDatabaseConnection implements DatabaseConnection {

    private DataSource dataSource;

    private Configuration configuration;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setupDatabase(DatabaseConfiguration setup) {
        dataSource = initializeDataSource(setup);
        configuration = initializeConfiguration();
    }

    private Configuration initializeConfiguration() {
        return new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.POSTGRES);
    }

    private DataSource initializeDataSource(DatabaseConfiguration setup) {
        HikariConfig config = new HikariConfig();
        String jdbcUrlBuilder = "jdbc:" +
                setup.getDbType() +
                "://" +
                setup.getDbHost() +
                ":" +
                setup.getDbPort() +
                "/" +
                setup.getDbBase() +
                setup.getDbOptions();
        config.setJdbcUrl(jdbcUrlBuilder);
        config.setDriverClassName(setup.getDbDriver());
        config.setUsername(setup.getDbUser());
        config.setPassword(setup.getDbPassword());
        config.setAutoCommit(true);
        config.setMaximumPoolSize(25);
        return new HikariDataSource(config);
    }

}
