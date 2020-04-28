package pl.subtelny.islands.repository.island.updater;

import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.repository.island.anemia.IslandAnemiaFactory;
import org.jooq.Configuration;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslandUpdater extends Updater<Island> {

    private final Configuration configuration;

	public IslandUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	public void updateIsland(Island island) {
        performAction(island);
    }

    @Override
    public void performAction(Island entity) {
        IslandAnemia islandAnemia = IslandAnemiaFactory.toAnemia(entity);
        IslandAnemiaUpdateAction action = determineIslandUpdateAction(entity.getIslandType());
        action.perform(islandAnemia);
    }

    @Override
    public CompletableFuture<Integer> performActionAsync(Island island) {

        return null;
    }

    private IslandAnemiaUpdateAction determineIslandUpdateAction(IslandType islandType) {
        if (islandType == IslandType.SKYBLOCK) {
            return new SkyblockIslandAnemiaUpdateAction(configuration);
        }
        throw new IllegalArgumentException("Not found update action for type " + islandType);
    }


}
