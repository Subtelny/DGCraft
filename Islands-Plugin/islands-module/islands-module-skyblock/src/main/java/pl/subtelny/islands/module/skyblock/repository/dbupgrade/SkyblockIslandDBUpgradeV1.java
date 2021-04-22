package pl.subtelny.islands.module.skyblock.repository.dbupgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class SkyblockIslandDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public SkyblockIslandDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("skyblock_islands")
                .column("island_id", SQLDataType.INTEGER.nullable(false).identity(true))
                .column("x", SQLDataType.INTEGER.nullable(false))
                .column("z", SQLDataType.INTEGER.nullable(false))
                .column("extend_level", SQLDataType.INTEGER.nullable(false).defaultValue(0))
                .constraints(
                        constraint("skyblock_islands_id_fk")
                                .foreignKey("island_id")
                                .references("islands", "id"),
                        constraint("skyblock_islands_id_pk")
                                .unique("island_id"),
                        constraint("skyblock_island_x_z_pk")
                                .primaryKey("x", "z"),
                        constraint("skyblock_islands_x_z_uq")
                                .unique("x", "z")
                )
                .execute();
    }

    @Override
    public int order() {
        return 3;
    }
}
