package pl.subtelny.islands.islander.repository.loader;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.island.IslanderId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IslanderLoader {

	private final DatabaseConnection databaseConfiguration;

	public IslanderLoader(DatabaseConnection databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	public Optional<Islander> loadIslander(IslanderLoadRequest request) {
		Optional<IslanderAnemia> anemiaOpt = performAction(request);
		return anemiaOpt.map(this::mapAnemiaIntoDomain);
	}

	public List<Islander> loadIslanders(IslanderLoadRequest request) {
		List<IslanderAnemia> anemiaOpt = performListAction(request);
		return anemiaOpt.stream()
				.map(this::mapAnemiaIntoDomain)
				.collect(Collectors.toList());
	}

	private Optional<IslanderAnemia> performAction(IslanderLoadRequest request) {
		IslanderAnemiaLoadAction action = getIslanderAnemiaLoadAction(request);
		return Optional.ofNullable(action.perform());
	}

	private List<IslanderAnemia> performListAction(IslanderLoadRequest request) {
		IslanderAnemiaLoadAction action = getIslanderAnemiaLoadAction(request);
		return action.performList();
	}

	private IslanderAnemiaLoadAction getIslanderAnemiaLoadAction(IslanderLoadRequest request) {
		Configuration configuration = databaseConfiguration.getConfiguration();
		return new IslanderAnemiaLoadAction(configuration, request);
	}

	private Islander mapAnemiaIntoDomain(IslanderAnemia anemia) {
		IslanderId islanderId = anemia.getIslanderId();
		return new Islander(islanderId);
	}

}
