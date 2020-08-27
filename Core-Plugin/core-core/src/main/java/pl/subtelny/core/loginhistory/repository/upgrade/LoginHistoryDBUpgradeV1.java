package pl.subtelny.core.loginhistory.repository.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class LoginHistoryDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public LoginHistoryDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("login_histories")
                .column("account", SQLDataType.UUID.nullable(false))
                .column("login_time", SQLDataType.LOCALDATETIME.nullable(false))
                .column("logout_time", SQLDataType.LOCALDATETIME.nullable(false))
                .constraints(
                        constraint("lh_account").primaryKey("account"),
                        constraint("lh_account_fk").foreignKey("account").references("accounts", "id")
                )
                .execute();
    }

    @Override
    public int order() {
        return 4;
    }
}
