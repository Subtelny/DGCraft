package pl.subtelny.islands.skyblockisland.islandmembership.remover;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.skyblockisland.islandmembership.model.IslandMembership;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IslandMembershipRemover {

    private final DatabaseConnection databaseConnection;

    public IslandMembershipRemover(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public CompletableFuture<Integer> removeAsync(List<IslanderId> islanderIdList) {
        IslandMembershipRemoveRequest removeRequest = IslandMembershipRemoveRequest.newBuilder()
                .where(islanderIdList.toArray(new IslanderId[0]))
                .build();
        return performAsync(removeRequest);
    }

    public CompletableFuture<Integer> removeAsync(IslandMembership islandMembership) {
        IslandMembershipRemoveRequest removeRequest = IslandMembershipRemoveRequest.newBuilder()
                .where(islandMembership.getIslanderId())
                .build();
        return performAsync(removeRequest);
    }

    public CompletableFuture<Integer> removeAsync(IslandId islandId) {
        IslandMembershipRemoveRequest removeRequest = IslandMembershipRemoveRequest.newBuilder()
                .where(islandId)
                .build();
        return performAsync(removeRequest);
    }

    private CompletableFuture<Integer> performAsync(IslandMembershipRemoveRequest request) {
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipRemoveAction action = new IslandMembershipRemoveAction(configuration, request);
        return action.performAsync();
    }
}
