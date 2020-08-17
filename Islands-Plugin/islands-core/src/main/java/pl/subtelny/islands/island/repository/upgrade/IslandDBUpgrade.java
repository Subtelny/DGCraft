package pl.subtelny.islands.island.repository.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;
import pl.subtelny.generated.tables.tables.Islands;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslandDBUpgrade implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslandDBUpgrade(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists(Islands.ISLANDS)
                .column(Islands.ISLANDS.ID)
                .column(Islands.ISLANDS.SPAWN)
                .column(Islands.ISLANDS.CREATED_DATE)
                .column("points", SQLDataType.INTEGER)
                .constraints(
                        constraint("islands_pk").primaryKey(Islands.ISLANDS.ID),
                        constraint("islands_uq").unique(Islands.ISLANDS.ID)
                )
                .execute();
    }

    @Override
    public int order() {
        return 2;
    }
}
