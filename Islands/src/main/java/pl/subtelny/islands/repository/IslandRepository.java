package pl.subtelny.islands.repository;

import java.util.List;
import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.loader.island.IslandAnemia;
import pl.subtelny.islands.repository.loader.island.IslandAnemiaLoader;
import pl.subtelny.islands.repository.loader.island.IslandAnemiaLoaderRequest;

@Component
public class IslandRepository {

	private final Configuration configuration;

	private final IslanderRepository islanderRepository;

	private final GuildRepository guildRepository;

	public IslandRepository(DatabaseConfiguration databaseConfiguration,
			IslanderRepository islanderRepository,
			GuildRepository guildRepository) {
		this.configuration = databaseConfiguration.getConfiguration();
		this.islanderRepository = islanderRepository;
		this.guildRepository = guildRepository;
	}

	public Optional<IslandAnemia> findIsland(IslandId islandId) {
		IslandAnemiaLoaderRequest request = IslandAnemiaLoaderRequest.newBuilder()
				.where(islandId)
				.build();
		return loadIsland(request);
	}

	private Optional<IslandAnemia> loadIsland(IslandAnemiaLoaderRequest request) {
		IslandAnemiaLoader loader = new IslandAnemiaLoader(configuration, request);
		List<IslandAnemia> loadedData = loader.perform().getLoadedData();
		if (loadedData.size() > 0) {
			return Optional.of(loadedData.get(0));
		}
		return Optional.empty();
	}
}
