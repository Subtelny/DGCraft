package pl.subtelny.islands.repository.island.loader;

import java.util.List;
import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.islands.repository.island.anemia.IslandMemberAnemia;
import pl.subtelny.repository.LoaderResult;

public abstract class IslandLoader<ANEMIA extends IslandAnemia, DOMAIN extends Island> {

	protected final Configuration configuration;

	protected IslandLoader(Configuration configuration) {
		this.configuration = configuration;
	}

	public Optional<DOMAIN> loadIsland(IslandAnemiaLoadAction<ANEMIA> loadAction) {
		Optional<ANEMIA> account = performAction(loadAction);
		if (account.isPresent()) {
			ANEMIA anemia = account.get();
			List<IslandMemberAnemia> islandMembers = loadIslandMembers(anemia.getIslandId());
			DOMAIN island = mapAnemiaToDomain(anemia, islandMembers);
			return Optional.of(island);
		}
		return Optional.empty();
	}

	private List<IslandMemberAnemia> loadIslandMembers(IslandId islandId) {
		IslandMemberAnemiaLoaderRequest request = IslandMemberAnemiaLoaderRequest.newBuilder().where(islandId).build();
		IslandMemberAnemiaLoadAction action = new IslandMemberAnemiaLoadAction(configuration, request);
		LoaderResult<List<IslandMemberAnemia>> perform = action.perform();
		return perform.getLoadedData();
	}

	private Optional<ANEMIA> performAction(IslandAnemiaLoadAction<ANEMIA> loadAction) {
		ANEMIA loadedData = loadAction.perform().getLoadedData();
		return Optional.ofNullable(loadedData);
	}

	protected abstract DOMAIN mapAnemiaToDomain(ANEMIA anemia, List<IslandMemberAnemia> islandMembers);
}
