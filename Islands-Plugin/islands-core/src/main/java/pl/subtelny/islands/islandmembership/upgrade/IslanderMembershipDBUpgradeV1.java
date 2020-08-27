package pl.subtelny.islands.islandmembership.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslanderMembershipDBUpgradeV1 implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslanderMembershipDBUpgradeV1(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("island_membership")
                .column("island_member_id", SQLDataType.VARCHAR.nullable(false))
                .column("island_id", SQLDataType.INTEGER.nullable(false))
                .column("rank", SQLDataType.VARCHAR(10).nullable(false))
                .constraints(
                        constraint("membership_island_island_uq").unique("island_member_id", "island_id"),
                        constraint("membership_island_island_pk").primaryKey("island_member_id", "island_id"),
                        constraint("membership_island_island_fk").foreignKey("island_id").references("islands", "id")
                ).execute();
    }

    @Override
    public int order() {
        return 4;
    }
}
