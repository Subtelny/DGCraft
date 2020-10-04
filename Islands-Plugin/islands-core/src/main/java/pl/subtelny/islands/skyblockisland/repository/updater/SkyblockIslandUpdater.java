package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class SkyblockIslandUpdater extends Updater<SkyblockIslandAnemia, IslandId> {

    public SkyblockIslandUpdater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    public IslandId update(SkyblockIslandAnemia skyblockIsland) {
        return performAction(skyblockIsland);
    }

    public IslandId update(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return performAction(anemia);
    }

    public CompletableFuture<IslandId> updateAsync(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return performActionAsync(anemia);
    }

    public CompletableFuture<IslandId> updateAsync(SkyblockIslandAnemia skyblockIsland) {
        return performActionAsync(skyblockIsland);
    }

    @Override
    protected IslandId performAction(SkyblockIslandAnemia skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        return action.perform(skyblockIsland);
    }

    @Override
    protected CompletableFuture<IslandId> performActionAsync(SkyblockIslandAnemia skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        return action.performAsync(skyblockIsland).toCompletableFuture();
    }

}
