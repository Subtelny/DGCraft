package pl.subtelny.islands.islandmembership.loader;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.islandmembership.anemia.IslandMembershipAnemia;

import java.util.Map;
import java.util.stream.Collectors;

public class IslandMembershipLoader {

    private final DatabaseConnection databaseConnection;

    public IslandMembershipLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Map<IslandMemberId, IslandMemberRank> loadMembership(IslandId islandId) {
        IslandMembershipLoadRequest request = IslandMembershipLoadRequest.newBuilder().where(islandId).build();
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipAnemiaLoadAction action = new IslandMembershipAnemiaLoadAction(configuration, request);
        return action.performList().stream()
                .collect(Collectors.toMap(IslandMembershipAnemia::getIslandMemberId, IslandMembershipAnemia::getRank));
    }

}
