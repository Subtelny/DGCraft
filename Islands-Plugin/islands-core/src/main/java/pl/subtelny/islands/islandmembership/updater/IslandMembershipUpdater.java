package pl.subtelny.islands.islandmembership.updater;

import org.jooq.Configuration;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandMemberRank;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islandmembership.anemia.IslandMembershipAnemia;
import pl.subtelny.islands.islandmembership.dto.IslandMembershipDTO;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class IslandMembershipUpdater extends Updater<IslandMembershipDTO, IslandMemberId> {

    public IslandMembershipUpdater(DatabaseConnection databaseConnection, TransactionProvider transactionProvider) {
        super(databaseConnection, transactionProvider);
    }

    public void update(IslandId islandId, IslandMemberId islanderId, IslandMemberRank rank) {
        performAction(new IslandMembershipDTO(islanderId, islandId, rank));
    }

    @Override
    protected IslandMemberId performAction(IslandMembershipDTO islandMembership) {
        IslandMembershipAnemiaUpdateAction action = createUpdateAction();
        IslandMembershipAnemia islandMembershipAnemia = toAnemia(islandMembership);
        return action.perform(islandMembershipAnemia);
    }

    @Override
    protected CompletableFuture<IslandMemberId> performActionAsync(IslandMembershipDTO islandMembership) {
        IslandMembershipAnemiaUpdateAction action = createUpdateAction();
        IslandMembershipAnemia islandMembershipAnemia = toAnemia(islandMembership);
        return action.performAsync(islandMembershipAnemia);
    }

    private IslandMembershipAnemiaUpdateAction createUpdateAction() {
        Configuration configuration = getConfiguration();
        return new IslandMembershipAnemiaUpdateAction(configuration);
    }

    private IslandMembershipAnemia toAnemia(IslandMembershipDTO islandMembership) {
        return new IslandMembershipAnemia(islandMembership.getIslandMemberId(), islandMembership.getIslandId(), islandMembership.getRank());
    }
}
