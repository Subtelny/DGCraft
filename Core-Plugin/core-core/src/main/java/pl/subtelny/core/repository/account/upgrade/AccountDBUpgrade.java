package pl.subtelny.core.repository.account.upgrade;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;
import pl.subtelny.generated.tables.tables.Accounts;

import static org.jooq.impl.DSL.constraint;

@Component
public class AccountDBUpgrade implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public AccountDBUpgrade(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        Configuration configuration = databaseConnection.getConfiguration();
        DSL.using(configuration)
                .createTableIfNotExists(Accounts.ACCOUNTS)
                .column(Accounts.ACCOUNTS.ID)
                .column(Accounts.ACCOUNTS.NAME)
                .column(Accounts.ACCOUNTS.DISPLAY_NAME)
                .column(Accounts.ACCOUNTS.CITY)
                .column(Accounts.ACCOUNTS.LAST_ONLINE)
                .constraints(
                        constraint("accounts_pk").primaryKey(Accounts.ACCOUNTS.ID),
                        constraint("accounts_uq").unique(Accounts.ACCOUNTS.ID)
                )
                .execute();
    }

    @Override
    public int order() {
        return 0;
    }

}
