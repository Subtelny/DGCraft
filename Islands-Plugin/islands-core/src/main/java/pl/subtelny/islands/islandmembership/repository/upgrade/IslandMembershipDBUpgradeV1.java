package pl.subtelny.islands.islandmembership.repository.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslandMembershipDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslandMembershipDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("island_memberships")
                .column("island_id", SQLDataType.INTEGER.nullable(false))
                .column("island_member_id", SQLDataType.VARCHAR(50).nullable(false))
                .column("owner", SQLDataType.BOOLEAN.nullable(false))
                .constraints(
                        constraint("island_membership_id")
                                .primaryKey("island_id", "island_member_id"),
                        constraint("island_id_fk")
                                .foreignKey("island_id")
                                .references("islands", "id"),
                        constraint("island_memberships_uq")
                                .unique("island_id", "island_member_id")
                )
                .execute();
    }

    @Override
    public int order() {
        return 5;
    }
}
