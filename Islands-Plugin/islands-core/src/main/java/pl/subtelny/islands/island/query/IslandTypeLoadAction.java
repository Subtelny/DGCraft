package pl.subtelny.islands.island.query;

import org.jooq.DSLContext;
import pl.subtelny.core.api.repository.LoadAction;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;

import java.util.Collections;
import java.util.List;

public class IslandTypeLoadAction implements LoadAction<IslandType> {

    private final IslandId islandId;

    private final DSLContext connection;

    public IslandTypeLoadAction(IslandId islandId, DSLContext connection) {
        this.islandId = islandId;
        this.connection = connection;
    }

    @Override
    public IslandType perform() {
        String islandTypeRaw = connection
                .select(Islands.ISLANDS.TYPE)
                .from(Islands.ISLANDS)
                .where(Islands.ISLANDS.ID.eq(islandId.getInternal()))
                .fetchOne(record -> record.get(Islands.ISLANDS.TYPE));
        return new IslandType(islandTypeRaw);
    }

    @Override
    public List<IslandType> performList() {
        return Collections.singletonList(perform());
    }

}
