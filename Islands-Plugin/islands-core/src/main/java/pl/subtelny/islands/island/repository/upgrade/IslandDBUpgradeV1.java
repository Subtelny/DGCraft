package pl.subtelny.islands.island.repository.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslandDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslandDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("islands")
                .column("id", SQLDataType.INTEGER.nullable(false).identity(true))
                .column("type", SQLDataType.VARCHAR.nullable(false))
                .column("spawn", SQLDataType.VARCHAR)
                .column("created_date", SQLDataType.LOCALDATETIME.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)))
                .column("points", SQLDataType.INTEGER.defaultValue(0).nullable(false))
                .constraints(
                        constraint("islands_id_pk").primaryKey("id"),
                        constraint("islands_id_uq").unique("id")
                )
                .execute();
    }

    @Override
    public int order() {
        return 2;
    }
}
