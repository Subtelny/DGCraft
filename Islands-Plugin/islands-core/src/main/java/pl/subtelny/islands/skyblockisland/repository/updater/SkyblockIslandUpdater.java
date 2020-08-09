package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class SkyblockIslandUpdater extends Updater<SkyblockIsland, SkyblockIslandId> {

    public SkyblockIslandUpdater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    public SkyblockIslandId update(SkyblockIsland skyblockIsland) {
        return performAction(skyblockIsland);
    }

    public CompletableFuture<SkyblockIslandId> updateAsync(SkyblockIsland skyblockIsland) {
        return performActionAsync(skyblockIsland);
    }

    @Override
    protected SkyblockIslandId performAction(SkyblockIsland skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return action.perform(anemia);
    }

    @Override
    protected CompletableFuture<SkyblockIslandId> performActionAsync(SkyblockIsland skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return action.performAsync(anemia).toCompletableFuture();
    }

}
