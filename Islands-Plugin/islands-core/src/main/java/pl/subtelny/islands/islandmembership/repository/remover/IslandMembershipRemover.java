package pl.subtelny.islands.islandmembership.repository.remover;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.repository.Remover;

import java.util.concurrent.CompletableFuture;

@Component
public class IslandMembershipRemover extends Remover<IslandMembershipRemoveRequest> {

    @Autowired
    public IslandMembershipRemover(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    @Override
    public void performAction(IslandMembershipRemoveRequest islandMembershipRemoveRequest) {
        IslandMembershipRemoverAction action = new IslandMembershipRemoverAction(getConfiguration());
        action.perform(islandMembershipRemoveRequest);
    }

    @Override
    public CompletableFuture<Integer> performActionAsync(IslandMembershipRemoveRequest islandMembershipRemoveRequest) {
        IslandMembershipRemoverAction action = new IslandMembershipRemoverAction(getConfiguration());
        return action.performAsync(islandMembershipRemoveRequest);
    }
}
