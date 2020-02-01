package pl.subtelny.islands.repository.island.updater;

import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.repository.island.anemia.IslandAnemiaFactory;
import pl.subtelny.repository.Updater;

@Component
public class IslandUpdater extends Updater<Island> {

    private final Configuration configuration;

    @Autowired
    public IslandUpdater(DatabaseConfiguration configuration) {
        this.configuration = configuration.getConfiguration();
    }

    public void updateIsland(Island island) {
        addToQueue(island);
    }

    @Override
    protected void performAction(Island island) {
        IslandAnemia islandAnemia = IslandAnemiaFactory.toAnemia(island);
        IslandAnemiaUpdateAction action = determineIslandUpdateAction(island.getIslandType());
        action.perform(islandAnemia);
    }

    private IslandAnemiaUpdateAction determineIslandUpdateAction(IslandType islandType) {
        if (islandType == IslandType.SKYBLOCK) {
            return new SkyblockIslandAnemiaUpdateAction(configuration);
        }
        throw new IllegalArgumentException("Not found update action for type " + islandType);
    }


}
