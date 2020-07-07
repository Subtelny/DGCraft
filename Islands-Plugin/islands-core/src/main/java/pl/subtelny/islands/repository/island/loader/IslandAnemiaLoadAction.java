package pl.subtelny.islands.repository.island.loader;

import org.jooq.*;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.repository.LoadAction;

import java.util.List;

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

	private ANEMIA fetchMapRecordIntoAnemia(Record record) {
		return mapRecordIntoAnemia(record);
	}

	protected abstract List<Condition> whereConditions();

	protected abstract ANEMIA mapRecordIntoAnemia(Record record);

	protected abstract SelectOnConditionStep<Record> addJoinIslandToQuery(SelectJoinStep<Record> from);

}
