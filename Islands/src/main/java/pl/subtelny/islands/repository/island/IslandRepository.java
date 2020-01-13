package pl.subtelny.islands.repository.island;

import java.util.Optional;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.generated.enums.Islandtype;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.storage.IslandStorage;

@Component
public class IslandRepository {

	private final SkyblockIslandRepository skyblockIslandRepository;

	private final IslandStorage islandStorage;

	private final Configuration configuration;

	@Autowired
	public IslandRepository(SkyblockIslandRepository skyblockIslandRepository,
			DatabaseConfiguration databaseConfiguration,
			IslandStorage islandStorage) {
		this.skyblockIslandRepository = skyblockIslandRepository;
		this.islandStorage = islandStorage;
		this.configuration = databaseConfiguration.getConfiguration();
	}

	public Optional<Island> findIsland(IslandId islandId) {
		return islandStorage.getCache(islandId, islandId1 -> {
			Optional<IslandType> islandTypeOpt = findIslandType(islandId1);
			if (islandTypeOpt.isPresent()) {
				IslandType islandType = islandTypeOpt.get();
				return loadIslandByIslandType(islandId1, islandType);
			}
			return Optional.empty();
		});
	}

	private Optional<IslandType> findIslandType(IslandId islandId) {
		Optional<Record1<Islandtype>> recordOptional = DSL.using(configuration)
				.select(Islands.ISLANDS.TYPE)
				.where(Islands.ISLANDS.ID.eq(islandId.getId().intValue()))
				.fetchOptional();

		if (recordOptional.isPresent()) {
			IslandType islandType = recordIslandTypeIntoDomain(recordOptional.get());
			return Optional.of(islandType);
		}
		return Optional.empty();
	}

	private IslandType recordIslandTypeIntoDomain(Record1<Islandtype> islandtypeRecord1) {
		Islandtype islandtype = islandtypeRecord1.value1();
		String literal = islandtype.getLiteral();
		return IslandType.valueOf(literal);
	}

	private Optional<Island> loadIslandByIslandType(IslandId islandId, IslandType type) {
		if (type == IslandType.SKYBLOCK) {
			Optional<SkyblockIsland> skyblockIslandOpt = skyblockIslandRepository.findSkyblockIsland(islandId);
			if (skyblockIslandOpt.isPresent()) {
				return Optional.of(skyblockIslandOpt.get());
			}
		}
		return Optional.empty();
	}

}
