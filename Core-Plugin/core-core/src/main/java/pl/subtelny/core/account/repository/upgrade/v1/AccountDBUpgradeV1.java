package pl.subtelny.core.account.repository.upgrade.v1;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class AccountDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public AccountDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        Configuration configuration = databaseConnection.getConfiguration();
        DSL.using(configuration)
                .createTableIfNotExists("accounts")
                .column("id", SQLDataType.UUID.nullable(false))
                .column("name", SQLDataType.VARCHAR(16).nullable(false))
                .column("display_name", SQLDataType.VARCHAR(16).nullable(false))
                .column("last_online", SQLDataType.LOCALDATETIME.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)))
                .constraints(
                        constraint("accounts_id_pk").primaryKey("id"),
                        constraint("accounts_id_uq").unique("id"),
                        constraint("accounts_name_uq").unique("name")
                )
                .execute();
    }

    @Override
    public int order() {
        return 0;
    }

}
