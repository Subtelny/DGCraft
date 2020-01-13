package pl.subtelny.islands.repository.island.loader;

import java.util.List;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.GuildIslands;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.repository.LoadAction;
import pl.subtelny.repository.LoaderResult;
import pl.subtelny.validation.ValidationException;

public abstract class IslandAnemiaLoadAction<ANEMIA> implements LoadAction<ANEMIA> {

	private final Configuration configuration;

	protected IslandAnemiaLoadAction(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public LoaderResult<ANEMIA> perform() {
		List<ANEMIA> islandAnemia = loadIslandAnemia();
		return new LoaderResult<ANEMIA>(islandAnemia);
	}

	private List<ANEMIA> loadIslandAnemia() {
		SelectJoinStep<Record> from = DSL.using(configuration)
				.select()
				.from(Islands.ISLANDS);
		return addJoinIslandToQuery(from)
				.where(whereConditions())
				.fetch(this::fetchMapRecordIntoAnemia);
	}

	private SelectOnConditionStep<Record> addJoinIslandToQuery(SelectJoinStep<Record> from) {
		IslandType islandType = getIslandType();
		if (islandType == IslandType.SKYBLOCK) {
			return from
					.leftOuterJoin(SkyblockIslands.SKYBLOCK_ISLANDS)
					.on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID));
		} else if (islandType == IslandType.GUILD) {
			return from
					.leftOuterJoin(GuildIslands.GUILD_ISLANDS)
					.on(Islands.ISLANDS.ID.eq(GuildIslands.GUILD_ISLANDS.ISLAND_ID));
		} else {
			throw new IllegalArgumentException("Not found join query for island type " + islandType.name());
		}
	}

	private ANEMIA fetchMapRecordIntoAnemia(Record record) {
		validateRecordHasCorrectIslandType(record);
		return mapRecordIntoAnemia(record);
	}

	private void validateRecordHasCorrectIslandType(Record record) {
		IslandType recordIslandType = IslandType.valueOf(record.get(Islands.ISLANDS.TYPE).getLiteral());
		IslandType islandTypeAction = getIslandType();
		if (islandTypeAction != recordIslandType) {
			throw ValidationException.of("Tried to load " + recordIslandType.name() + " island as " + islandTypeAction);
		}
	}

	protected abstract List<Condition> whereConditions();

	protected abstract ANEMIA mapRecordIntoAnemia(Record record);

	protected abstract IslandType getIslandType();
}
