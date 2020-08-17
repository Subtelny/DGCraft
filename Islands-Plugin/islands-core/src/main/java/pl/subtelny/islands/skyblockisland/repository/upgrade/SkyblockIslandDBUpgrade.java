package pl.subtelny.islands.skyblockisland.repository.upgrade;

import org.jooq.impl.DSL;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.SkyblockIslands;

import static org.jooq.impl.DSL.constraint;

@Component
public class SkyblockIslandDBUpgrade implements DatabaseUpgrade {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public SkyblockIslandDBUpgrade(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void execute() {
        DSL.using(databaseConnection.getConfiguration())
                .createTableIfNotExists(SkyblockIslands.SKYBLOCK_ISLANDS)
                .column(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID)
                .column(SkyblockIslands.SKYBLOCK_ISLANDS.X)
                .column(SkyblockIslands.SKYBLOCK_ISLANDS.Z)
                .column(SkyblockIslands.SKYBLOCK_ISLANDS.EXTEND_LEVEL)
                .constraints(
                        constraint("skyblock_islands_id_foreign")
                                .foreignKey(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID)
                                .references(Islands.ISLANDS, Islands.ISLANDS.ID),
                        constraint("skyblock_islands_id_pk")
                                .unique(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID),
                        constraint("skyblock_island_x_z_pk")
                                .primaryKey(SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z),
                        constraint("skyblock_islands_x_z_uq")
                                .unique(SkyblockIslands.SKYBLOCK_ISLANDS.X, SkyblockIslands.SKYBLOCK_ISLANDS.Z)
                )
                .execute();
    }

    @Override
    public int order() {
        return 3;
    }
}
