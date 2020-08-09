package pl.subtelny.islands.skyblockisland.islandmembership.loader;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.islands.skyblockisland.model.MembershipType;

import java.util.Map;
import java.util.stream.Collectors;

public class IslandMembershipLoader {

    private final DatabaseConnection databaseConnection;

    public IslandMembershipLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Map<IslanderId, MembershipType> loadMembership(IslandId islandId) {
        IslandMembershipLoadRequest request = IslandMembershipLoadRequest.newBuilder().where(islandId).build();
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipAnemiaLoadAction action = new IslandMembershipAnemiaLoadAction(configuration, request);
        return action.performList().stream()
                .collect(Collectors.toMap(IslandMembershipAnemia::getIslanderId, IslandMembershipAnemia::getMembershipType));
    }

}
