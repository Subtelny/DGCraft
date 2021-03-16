package pl.subtelny.islands.island.repository.dbupgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslandConfigurationDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslandConfigurationDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("island_configurations")
                .column("island_id", SQLDataType.INTEGER.nullable(false).identity(true))
                .column("configuration", SQLDataType.JSON.nullable(false))
                .constraints(
                        constraint("island_id_pk").primaryKey("island_id"),
                        constraint("island_id_uq").unique("island_id"),
                        constraint("island_id_foreign")
                                .foreignKey("island_id")
                                .references("islands", "id")
                )
                .execute();
    }

    @Override
    public int order() {
        return 6;
    }
}
