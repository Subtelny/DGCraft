package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandAnemiaFactory;
import pl.subtelny.islands.island.skyblockisland.repository.SkyblockIslandAnemiaUpdateAction;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.island.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.core.api.repository.Updater;

public class SkyblockIslandUpdater extends Updater<SkyblockIslandAnemia, IslandId> {

    public SkyblockIslandUpdater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    public IslandId update(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return performAction(anemia);
    }

    @Override
    protected IslandId performAction(SkyblockIslandAnemia skyblockIsland) {
        Configuration configuration = getConfiguration();
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        return action.perform(skyblockIsland);
    }

}
