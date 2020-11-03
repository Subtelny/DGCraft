package pl.subtelny.islands.islander.repository.loader;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.core.api.repository.LoadAction;
import pl.subtelny.utilities.identity.BasicIdentity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<UUID> islanderUuids = request.getIslanderIds().stream().map(BasicIdentity::getInternal).collect(Collectors.toList());
        if (!islanderUuids.isEmpty()) {
            conditions.add(Islanders.ISLANDERS.ID.in(islanderUuids));
        }
        return conditions;
    }

    private IslanderAnemia mapIntoAnemia(Record record) {
        UUID uuid = record.get(Islanders.ISLANDERS.ID);
        IslanderId islanderId = IslanderId.of(uuid);
        return new IslanderAnemia(islanderId);
    }
}
