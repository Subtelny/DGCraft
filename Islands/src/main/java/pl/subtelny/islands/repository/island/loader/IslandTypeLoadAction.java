package pl.subtelny.islands.repository.island.loader;

import java.util.Optional;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.enums.Islandtype;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.repository.LoadAction;
import pl.subtelny.repository.LoaderResult;

public class IslandTypeLoadAction implements LoadAction<Optional<IslandType>> {

	private final Configuration configuration;

	private final IslandId islandId;

	public IslandTypeLoadAction(Configuration configuration, IslandId islandId) {
		this.configuration = configuration;
		this.islandId = islandId;
	}

	@Override
	public LoaderResult<Optional<IslandType>> perform() {
		Optional<IslandType> islandTypeOpt = findIslandType(islandId);
		return new LoaderResult<>(islandTypeOpt);
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

}
