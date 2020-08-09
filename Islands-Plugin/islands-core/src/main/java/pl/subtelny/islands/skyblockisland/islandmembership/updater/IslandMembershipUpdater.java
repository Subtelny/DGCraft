package pl.subtelny.islands.skyblockisland.islandmembership.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.islander.model.IslandId;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.islands.skyblockisland.islandmembership.dto.IslandMembershipDTO;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslandMembershipUpdater extends Updater<IslandMembershipDTO, IslanderId> {

    public IslandMembershipUpdater(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        super(databaseConnection, transactionProvider);
    }

    public void update(IslandId islandId, IslanderId islanderId, MembershipType type) {
        performAction(new IslandMembershipDTO(islanderId, islandId, type));
    }

    @Override
    protected IslanderId performAction(IslandMembershipDTO islandMembership) {
        IslandMembershipAnemiaUpdateAction action = createUpdateAction();
        IslandMembershipAnemia islandMembershipAnemia = toAnemia(islandMembership);
        return action.perform(islandMembershipAnemia);
    }

    @Override
    protected CompletableFuture<IslanderId> performActionAsync(IslandMembershipDTO islandMembership) {
        IslandMembershipAnemiaUpdateAction action = createUpdateAction();
        IslandMembershipAnemia islandMembershipAnemia = toAnemia(islandMembership);
        return action.performAsync(islandMembershipAnemia);
    }

    private IslandMembershipAnemiaUpdateAction createUpdateAction() {
        Configuration configuration = getConfiguration();
        return new IslandMembershipAnemiaUpdateAction(configuration);
    }

    private IslandMembershipAnemia toAnemia(IslandMembershipDTO islandMembership) {
        return new IslandMembershipAnemia(islandMembership.getIslanderId(), islandMembership.getIslandId(), islandMembership.getMembershipType());
    }
}
