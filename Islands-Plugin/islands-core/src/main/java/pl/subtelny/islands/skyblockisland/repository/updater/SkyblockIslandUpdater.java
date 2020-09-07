package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class SkyblockIslandUpdater extends Updater<SkyblockIslandAnemia, SkyblockIslandId> {

    public SkyblockIslandUpdater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    public SkyblockIslandId update(SkyblockIslandAnemia skyblockIsland) {
        return performAction(skyblockIsland);
    }

    public SkyblockIslandId update(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return performAction(anemia);
    }

    public CompletableFuture<SkyblockIslandId> updateAsync(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return performActionAsync(anemia);
    }

    public CompletableFuture<SkyblockIslandId> updateAsync(SkyblockIslandAnemia skyblockIsland) {
        return performActionAsync(skyblockIsland);
    }

    @Override
    protected SkyblockIslandId performAction(SkyblockIslandAnemia skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        return action.perform(skyblockIsland);
    }

    @Override
    protected CompletableFuture<SkyblockIslandId> performActionAsync(SkyblockIslandAnemia skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        return action.performAsync(skyblockIsland).toCompletableFuture();
    }

}
