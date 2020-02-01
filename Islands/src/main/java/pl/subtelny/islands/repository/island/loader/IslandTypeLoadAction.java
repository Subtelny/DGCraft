package pl.subtelny.islands.repository.island.loader;

import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.enums.Islandtype;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.repository.LoadAction;
import pl.subtelny.repository.LoaderResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IslandTypeLoadAction implements LoadAction<Optional<IslandType>> {

	private final Configuration configuration;

	private final IslandId islandId;

	public IslandTypeLoadAction(Configuration configuration, IslandId islandId) {
		this.configuration = configuration;
		this.islandId = islandId;
	}

	@Override
	public Optional<IslandType> perform() {
		return findIslandType(islandId);
	}

	@Override
	public List<Optional<IslandType>> performList() {
		return Collections.singletonList(perform());
	}

	private Optional<IslandType> findIslandType(IslandId islandId) {
		Optional<Record1<Islandtype>> recordOptional = DSL.using(configuration)
				.select(Islands.ISLANDS.TYPE)
				.where(Islands.ISLANDS.ID.eq(islandId.getId()))
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
