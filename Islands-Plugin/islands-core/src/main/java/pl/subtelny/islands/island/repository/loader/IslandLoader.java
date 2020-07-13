package pl.subtelny.islands.island.repository.loader;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;

import java.util.Optional;

public abstract class IslandLoader<ANEMIA extends IslandAnemia, DOMAIN extends Island> {

	protected final DatabaseConnection databaseConfiguration;

	protected IslandLoader(DatabaseConnection databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	public Optional<DOMAIN> loadIsland(IslandAnemiaLoadAction<ANEMIA> loadAction) {
		Optional<ANEMIA> account = performAction(loadAction);
		if (account.isPresent()) {
			ANEMIA anemia = account.get();
			DOMAIN island = mapAnemiaToDomain(anemia);
			return Optional.of(island);
		}
		return Optional.empty();
	}


	private Optional<ANEMIA> performAction(IslandAnemiaLoadAction<ANEMIA> loadAction) {
		ANEMIA loadedData = loadAction.perform();
		return Optional.ofNullable(loadedData);
	}

	protected abstract DOMAIN mapAnemiaToDomain(ANEMIA anemia);
}
