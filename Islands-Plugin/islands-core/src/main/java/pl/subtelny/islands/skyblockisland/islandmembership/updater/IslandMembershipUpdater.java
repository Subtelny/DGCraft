package pl.subtelny.islands.skyblockisland.islandmembership.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.islands.skyblockisland.islandmembership.model.IslandMembership;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslandMembershipUpdater extends Updater<IslandMembership, IslanderId> {

    private final DatabaseConnection databaseConnection;

    public IslandMembershipUpdater(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public IslanderId update(IslandMembership islandMembership) {
        return performAction(islandMembership);
    }

    public CompletableFuture<IslanderId> updateAsync(IslandMembership islandMembership) {
        return performActionAsync(islandMembership);
    }

    @Override
    protected IslanderId performAction(IslandMembership islandMembership) {
        IslandMembershipAnemiaUpdateAction action = createUpdateAction();
        IslandMembershipAnemia islandMembershipAnemia = toAnemia(islandMembership);
        return action.perform(islandMembershipAnemia);
    }

    @Override
    protected CompletableFuture<IslanderId> performActionAsync(IslandMembership islandMembership) {
        IslandMembershipAnemiaUpdateAction action = createUpdateAction();
        IslandMembershipAnemia islandMembershipAnemia = toAnemia(islandMembership);
        return action.performAsync(islandMembershipAnemia);
    }

    private IslandMembershipAnemiaUpdateAction createUpdateAction() {
        Configuration configuration = databaseConnection.getConfiguration();
        return new IslandMembershipAnemiaUpdateAction(configuration);
    }

    private IslandMembershipAnemia toAnemia(IslandMembership islandMembership) {
        return new IslandMembershipAnemia(islandMembership.getIslanderId(), islandMembership.getIslandId(), islandMembership.getMembershipType());
    }
}
