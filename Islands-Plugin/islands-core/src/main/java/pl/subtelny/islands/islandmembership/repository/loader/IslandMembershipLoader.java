package pl.subtelny.islands.islandmembership.repository.loader;

import org.jooq.Configuration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.islandmembership.repository.anemia.IslandMembershipAnemia;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IslandMembershipLoader {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public IslandMembershipLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<IslandMembership> loadIslandMemberIds(IslandId islandId) {
        IslandMembershipLoadRequest request = IslandMembershipLoadRequest.builder().islandId(islandId).build();
        Configuration configuration = databaseConnection.getConfiguration();
        IslandMembershipAnemiaLoadAction action = new IslandMembershipAnemiaLoadAction(request, configuration);
        return mapAnemiasIntoDomain(action.performList());
    }

    private List<IslandMembership> mapAnemiasIntoDomain(List<IslandMembershipAnemia> anemias) {
        return anemias.stream()
                .map(this::mapAnemiaIntoDomain)
                .collect(Collectors.toList());
    }

    private IslandMembership mapAnemiaIntoDomain(IslandMembershipAnemia anemia) {
        IslandId islandId = IslandId.of(anemia.getIslandId());
        IslandMemberId islandMemberId = IslandMemberId.of(anemia.getIslandMemberId());
        return new IslandMembership(islandId, islandMemberId, anemia.isOwner());
    }

}
