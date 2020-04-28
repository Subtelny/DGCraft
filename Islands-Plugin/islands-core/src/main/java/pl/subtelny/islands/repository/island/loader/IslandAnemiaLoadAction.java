package pl.subtelny.islands.repository.island.loader;

import java.util.List;

import pl.subtelny.islands.model.island.IslandType;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.GuildIslands;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.repository.LoadAction;
import pl.subtelny.utilities.exception.ValidationException;

public abstract class IslandAnemiaLoadAction<ANEMIA> implements LoadAction<ANEMIA> {

	private final Configuration configuration;

	protected IslandAnemiaLoadAction(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public ANEMIA perform() {
		return loadOneIslandAnemia();
	}

	@Override
	public List<ANEMIA> performList() {
		return loadAllIslandAnemia();
	}

	private ANEMIA loadOneIslandAnemia() {
		return constructQuery()
				.fetchOne(this::fetchMapRecordIntoAnemia);
	}

	private List<ANEMIA> loadAllIslandAnemia() {
		return constructQuery()
				.fetch(this::fetchMapRecordIntoAnemia);
	}

	private SelectConditionStep<Record> constructQuery() {
		SelectJoinStep<Record> from = DSL.using(configuration)
				.select()
				.from(Islands.ISLANDS);
		return addJoinIslandToQuery(from)
				.where(whereConditions());
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