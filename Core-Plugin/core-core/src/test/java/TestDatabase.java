import com.google.common.collect.Sets;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.Configuration;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.meta.derby.sys.Sys;
import org.junit.Test;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.database.DatabaseConfiguration;
import pl.subtelny.core.repository.account.AccountRepository;
import pl.subtelny.core.repository.account.loader.AccountAnemiaLoadAction;
import pl.subtelny.core.repository.account.loader.AccountLoadRequest;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.records.AccountsRecord;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDatabase {

    public static String DB_DRIVER = "org.postgresql.Driver";

    public static String DB_TYPE = "postgresql";

    public static String DB_HOST = "localhost";

    public static String DB_PORT = "5432";

    public static String DB_BASE = "dgcraft";

    public static String DB_OPTIONS = "?serverTimezone=UTC&autoReconnect=true&useSSL=false";

    public static String DB_USER = "postgres";

    public static String DB_PASSWORD = "admin";

    @Test
    public void test2() {
        Set<Integer> ints = Sets.newHashSet(1,2,3);
        Set<Integer> ints2 = Sets.newHashSet(3,4,5);

        Set<Integer> collect = Stream.concat(ints.stream(), ints2.stream()).collect(Collectors.toSet());
        System.out.println(Arrays.toString(collect.toArray()));
    }

    @Test
    public void test() {
        AccountRepository repository = new AccountRepository(new DatabaseConnection() {
            @Override
            public DataSource getDataSource() {
                return null;
            }

            @Override
            public Configuration getConfiguration() {
                return initializeConfiguration();
            }
        });

        Optional<Account> account = repository.findAccount(AccountId.of(UUID.fromString("3d94a195-9053-3e7c-b228-a25dcaa25621")));
        System.out.println(account);
    }

    private Configuration initializeConfiguration() {
        return new DefaultConfiguration()
                .set(initializeDataSource(null))
                .set(SQLDialect.POSTGRES);
    }

    private DataSource initializeDataSource(DatabaseConfiguration setup) {
        HikariConfig config = new HikariConfig();
        String jdbcUrlBuilder = "jdbc:" +
                DB_TYPE +
                "://" +
                DB_HOST +
                ":" +
                DB_PORT +
                "/" +
                DB_BASE +
                DB_OPTIONS;
        config.setJdbcUrl(jdbcUrlBuilder);
        config.setDriverClassName(DB_DRIVER);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setAutoCommit(true);
        config.setMaximumPoolSize(25);
        return new HikariDataSource(config);
    }

}
