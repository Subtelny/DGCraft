package pl.subtelny.islands.islander.repository.loader;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.repository.LoadAction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IslanderAnemiaLoadAction implements LoadAction<IslanderAnemia> {

    private final Configuration configuration;

    private final IslanderLoadRequest request;

    public IslanderAnemiaLoadAction(Configuration configuration, IslanderLoadRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public IslanderAnemia perform() {
        return DSL.using(configuration)
                .select()
                .from(Islanders.ISLANDERS)
                .where(whereConditions())
                .fetchOne(this::mapIntoAnemia);
    }

    @Override
    public List<IslanderAnemia> performList() {
        return DSL.using(configuration)
                .select()
                .from(Islanders.ISLANDERS)
                .where(whereConditions())
                .fetch(this::mapIntoAnemia);
    }

    private List<Condition> whereConditions() {
        List<Condition> conditions = Lists.newArrayList();
        Optional<IslanderId> islanderIdOpt = request.getIslanderId();
        islanderIdOpt.ifPresent(islanderId -> conditions.add(Islanders.ISLANDERS.ID.eq(islanderId.getId())));
        return conditions;
    }

    private IslanderAnemia mapIntoAnemia(Record record) {
        UUID uuid = record.get(Islanders.ISLANDERS.ID);
        IslanderId islanderId = IslanderId.of(uuid);
        return new IslanderAnemia(islanderId);
    }
}
