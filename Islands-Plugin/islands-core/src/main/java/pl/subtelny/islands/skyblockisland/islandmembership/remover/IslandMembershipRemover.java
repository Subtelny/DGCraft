package pl.subtelny.islands.skyblockisland.islandmembership.remover;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.model.MembershipType;

import java.util.Arrays;
import java.util.Map;

public class IslandMembershipRemover {

    private final DatabaseConnection databaseConnection;

    public IslandMembershipRemover(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void remove(IslandId islandId, IslanderId... islanderIds) {
        IslandMembershipRemoveRequest request = IslandMembershipRemoveRequest.newBuilder()
                .where(islandId)
                .where(islanderIds)
                .build();
        performAction(request);
    }

    public void removeNotIn(IslandId islandId,  IslanderId... islanderIds) {
        IslandMembershipRemoveRequest request = IslandMembershipRemoveRequest.newBuilder()
                .where(islandId)
                .notIn(islanderIds)
                .build();
        performAction(request);
    }

    private void performAction(IslandMembershipRemoveRequest request) {
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipRemoveAction action = new IslandMembershipRemoveAction(configuration, request);
        action.perform();
    }

}
