package pl.subtelny.islands.islander.repository.upgrade;

import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import static org.jooq.impl.DSL.constraint;

@Component
public class IslanderMembershipDBUpgrade implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslanderMembershipDBUpgrade(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists("islanders_membership")
                .column("islander_id", SQLDataType.UUID)
                .column("island_id", SQLDataType.INTEGER)
                .column("rank", SQLDataType.VARCHAR(10))
                .constraints(
                        constraint("membership_islander_island_uq").unique("islander_id", "island_id"),
                        constraint("membership_islander_island_pk").primaryKey("islander_id", "island_id")
                ).execute();
    }

    @Override
    public int order() {
        return 4;
    }
}
