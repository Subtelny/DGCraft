package pl.subtelny.islands.repository.island.loader;

import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import org.jooq.Configuration;

import java.util.Optional;

public abstract class IslandLoader<ANEMIA extends IslandAnemia, DOMAIN extends Island> {

	protected final Configuration configuration;

	protected IslandLoader(Configuration configuration) {
		this.configuration = configuration;
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
