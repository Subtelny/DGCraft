package pl.subtelny.islands.island.repository.loader;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.repository.LoadAction;

import java.util.List;

public class IslandTypeLoadAction implements LoadAction<IslandType> {

    private final Configuration configuration;

    private final List<IslandId> islandIds;

    public IslandTypeLoadAction(Configuration configuration, List<IslandId> islandIds) {
        this.configuration = configuration;
        this.islandIds = islandIds;
    }

    @Override
    public IslandType perform() {
        return constructQuery()
                .fetchAny(this::mapRecordIntoType);
    }

    @Override
    public List<IslandType> performList() {
        return constructQuery()
                .fetch(this::mapRecordIntoType);
    }

    private SelectConditionStep<Record> constructQuery() {
        return DSL.using(configuration)
                .select()
                .from(Islands.ISLANDS)
                .where(whereConditions());
    }

    private List<Condition> whereConditions() {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add(Islands.ISLANDS.ID.in(islandIds));
        return conditions;
    }

    private IslandType mapRecordIntoType(Record record) {
        String type = record.get(Islands.ISLANDS.TYPE);
        return new IslandType(type);
    }

}
