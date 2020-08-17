package pl.subtelny.islands.islander.repository.upgrade;

import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;
import pl.subtelny.generated.tables.tables.Accounts;
import pl.subtelny.generated.tables.tables.Islanders;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslanderDBUpgrade implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslanderDBUpgrade(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists(Islanders.ISLANDERS)
                .column(Islanders.ISLANDERS.ID)
                .constraints(
                        constraint("islanders_pk").primaryKey(Islanders.ISLANDERS.ID),
                        constraint("islanders_uq").unique(Islanders.ISLANDERS.ID),
                        constraint("islanders_id_foreign")
                                .foreignKey(Islanders.ISLANDERS.ID).references(Accounts.ACCOUNTS, Accounts.ACCOUNTS.ID)
                )
                .execute();
    }

    @Override
    public int order() {
        return 1;
    }

}
