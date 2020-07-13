package pl.subtelny.islands.islander.repository.loader;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;

import java.util.Optional;

public class IslanderLoader {

	private final DatabaseConnection databaseConfiguration;

	public IslanderLoader(DatabaseConnection databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	public Optional<Islander> loadIslander(IslanderLoadRequest request) {
		Optional<IslanderAnemia> anemiaOpt = performAction(request);
		if (anemiaOpt.isPresent()) {
			Islander islander = mapAnemiaIntoDomain(anemiaOpt.get());
			return Optional.of(islander);
		}
		return Optional.empty();
	}

	private Optional<IslanderAnemia> performAction(IslanderLoadRequest request) {
		Configuration configuration = databaseConfiguration.getConfiguration();
		IslanderAnemiaLoadAction action = new IslanderAnemiaLoadAction(configuration, request);
		return Optional.ofNullable(action.perform());
	}

	private Islander mapAnemiaIntoDomain(IslanderAnemia anemia) {
		IslanderId islanderId = anemia.getIslanderId();
		return new Islander(islanderId);
	}

}
