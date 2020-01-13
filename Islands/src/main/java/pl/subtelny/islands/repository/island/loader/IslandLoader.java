package pl.subtelny.islands.repository.island.loader;

import java.util.List;
import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.SkyblockIslandAnemia;

public abstract class IslandLoader<ANEMIA, DOMAIN> {

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
		List<ANEMIA> loadedData = loadAction.perform().getLoadedData();
		if (loadedData.size() == 0) {
			return Optional.empty();
		}
		return Optional.of(loadedData.get(0));
	}

	protected abstract DOMAIN mapAnemiaToDomain(ANEMIA anemia);
}
