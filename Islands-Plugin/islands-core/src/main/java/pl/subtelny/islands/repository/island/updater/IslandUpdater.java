package pl.subtelny.islands.repository.island.updater;

import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.repository.island.anemia.IslandAnemiaFactory;
import org.jooq.Configuration;
import pl.subtelny.repository.Updater;

public class IslandUpdater extends Updater<Island> {

    private final Configuration configuration;

	public IslandUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	public void updateIsland(Island island) {
        addToQueue(island);
    }

    @Override
    protected void performAction(Island entity) {
        IslandAnemia islandAnemia = IslandAnemiaFactory.toAnemia(entity);
        IslandAnemiaUpdateAction action = determineIslandUpdateAction(entity.getIslandType());
        action.perform(islandAnemia);
    }

    private IslandAnemiaUpdateAction determineIslandUpdateAction(IslandType islandType) {
        if (islandType == IslandType.SKYBLOCK) {
            return new SkyblockIslandAnemiaUpdateAction(configuration);
        }
        throw new IllegalArgumentException("Not found update action for type " + islandType);
    }


}
