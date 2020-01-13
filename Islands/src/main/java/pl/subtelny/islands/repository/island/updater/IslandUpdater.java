package pl.subtelny.islands.repository.island.updater;

import org.jooq.Configuration;
import pl.subtelny.islands.repository.island.IslandAnemia;
import pl.subtelny.repository.Updater;

public class IslandUpdater extends Updater<IslandAnemia> {

	private final Configuration configuration;

	public IslandUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	public void updateIslandAnemia(IslandAnemia islandAnemia) {
		addToQueue(islandAnemia);
	}

	@Override
	protected void performAction(IslandAnemia islandAnemia) {
		IslandAnemiaUpdateAction action = new IslandAnemiaUpdateAction(configuration);
		action.perform(islandAnemia);
	}
}
