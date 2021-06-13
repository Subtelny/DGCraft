package pl.subtelny.islands.island.repository;

import org.jooq.*;
import org.jooq.Record;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.core.api.repository.LoadAction;

import java.util.List;

public abstract class IslandAnemiaLoadAction<ANEMIA> implements LoadAction<ANEMIA> {

	private final DSLContext connection;

	protected IslandAnemiaLoadAction(DSLContext connection) {
		this.connection = connection;
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
		SelectJoinStep<Record> from = connection.select()
				.from(Islands.ISLANDS);
		return addJoinIslandToQuery(from)
				.where(whereConditions());
	}

	private ANEMIA fetchMapRecordIntoAnemia(Record record) {
		return mapRecordIntoAnemia(record);
	}

	protected abstract List<Condition> whereConditions();

	protected abstract ANEMIA mapRecordIntoAnemia(Record record);

	protected abstract SelectOnConditionStep<Record> addJoinIslandToQuery(SelectJoinStep<Record> from);

}
