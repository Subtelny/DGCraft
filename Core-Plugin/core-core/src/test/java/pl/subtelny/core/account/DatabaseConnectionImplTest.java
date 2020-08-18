package pl.subtelny.core.account;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.database.DatabaseConfiguration;

import javax.sql.DataSource;

public class DatabaseConnectionImplTest implements DatabaseConnection {

    private final DatabaseConfiguration setup;

    private static final String DRIVER = "org.postgresql.Driver";

    private static final String TYPE = "postgresql";

    private static final String HOST = "localhost";

    private static final String PORT = "5432";

    private static final String BASE = "dev";

    private static final String OPTIONS = "?serverTimezone=UTC&autoReconnect=true&useSSL=false";

    private static final String USER = "postgres";

    private static final String PASSWORD = "admin";

    public DatabaseConnectionImplTest() {
        this.setup = new DatabaseConfiguration(TYPE, HOST, PORT, BASE, OPTIONS, DRIVER, USER, PASSWORD);
    }

    @Override
    public DataSource getDataSource() {
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

    @Override
    public Configuration getConfiguration() {
        return new DefaultConfiguration()
                .set(getDataSource())
                .set(SQLDialect.POSTGRES);
    }

}
