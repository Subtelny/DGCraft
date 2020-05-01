import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.junit.Test;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.generated.tables.enums.Citytype;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.records.AccountsRecord;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestDatabase {

    @Test
    public void test() {
        Configuration configuration = initializeConfiguration();

        AccountsRecord record = DSL.using(configuration).newRecord(Accounts.ACCOUNTS);
        record.setCity(Citytype.RED);
        record.setDisplayName("DisplayName2");
        record.setName("Name");
        record.setLastOnline(Timestamp.valueOf(LocalDateTime.now()));
        record.setId(UUID.fromString("57248137-2e79-49d8-bb70-7de8c5b742fc"));

        DSL.using(configuration)
                .insertInto(Accounts.ACCOUNTS)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record)
                .execute();
    }

    private Configuration initializeConfiguration() {
        return new DefaultConfiguration()
                .set(initializeDataSource())
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

}
