package pl.subtelny.islands.islandmembership.remover;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import java.util.Set;

public class IslandMembershipRemover {

    private final DatabaseConnection databaseConnection;

    public IslandMembershipRemover(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void remove(IslandId islandId, Set<IslandMember> islandMembers) {
        IslandMemberId[] islandMemberIds = toIslandMemberIds(islandMembers);
        IslandMembershipRemoveRequest request = IslandMembershipRemoveRequest.newBuilder()
                .where(islandId)
                .where(islandMemberIds)
                .build();
        performAction(request);
    }

    public void removeNotIn(IslandId islandId,  Set<IslandMember> islandMembers) {
        IslandMemberId[] islandMemberIds = toIslandMemberIds(islandMembers);
        IslandMembershipRemoveRequest request = IslandMembershipRemoveRequest.newBuilder()
                .where(islandId)
                .notIn(islandMemberIds)
                .build();
        performAction(request);
    }

    private IslandMemberId[] toIslandMemberIds(Set<IslandMember> islandMembers) {
        return islandMembers.stream()
                .map(IslandMember::getId)
                .toArray(i -> new IslandMemberId[0]);
    }

    private void performAction(IslandMembershipRemoveRequest request) {
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipRemoveAction action = new IslandMembershipRemoveAction(configuration, request);
        action.perform();
    }

}
