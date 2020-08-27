package pl.subtelny.islands.islander.repository.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslanderDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslanderDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("islanders")
                .column("id", SQLDataType.UUID.nullable(false))
                .constraints(
                        constraint("islanders_id_pk").primaryKey("id"),
                        constraint("islanders_id_uq").unique("id"),
                        constraint("islanders_id_foreign")
                                .foreignKey("id")
                                .references("accounts", "id")
                )
                .execute();
    }

    @Override
    public int order() {
        return 1;
    }

}
