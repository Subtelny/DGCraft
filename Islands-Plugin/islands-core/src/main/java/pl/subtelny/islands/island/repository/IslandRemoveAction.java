package pl.subtelny.islands.island.repository;

import org.jooq.DSLContext;
import pl.subtelny.core.api.repository.RemoveAction;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.islands.island.IslandId;

public class IslandRemoveAction implements RemoveAction<IslandId> {

    private final DSLContext connection;

    public IslandRemoveAction(DSLContext connection) {
        this.connection = connection;
    }

    @Override
    public void perform(IslandId islandId) {
        cascadeDeleteIsland(islandId);
    }

    private void cascadeDeleteIsland(IslandId islandId) {
        String deleteIslandCascade = "DELETE CASCADE FROM " + Islands.ISLANDS.getName() + " WHERE id = " + islandId;
        connection.execute(deleteIslandCascade);
    }

}
