package pl.subtelny.islands.islandmembership.repository.updater;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.islandmembership.repository.anemia.IslandMembershipAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

@Component
public class IslandMembershipUpdater extends Updater<IslandMembershipUpdateRequest, Integer> {

    @Autowired
    public IslandMembershipUpdater(DatabaseConnection databaseConfiguration, TransactionProvider transactionProvider) {
        super(databaseConfiguration, transactionProvider);
    }

    @Override
    public Integer performAction(IslandMembershipUpdateRequest islandMembershipUpdateRequest) {
        IslandMembershipAnemia anemia = toAnemia(islandMembershipUpdateRequest);
        return getAction().perform(anemia);
    }

    @Override
    public CompletableFuture<Integer> performActionAsync(IslandMembershipUpdateRequest islandMembershipUpdateRequest) {
        IslandMembershipAnemia anemia = toAnemia(islandMembershipUpdateRequest);
        return getAction().performAsync(anemia);
    }

    private IslandMembershipUpdateAction getAction() {
        return new IslandMembershipUpdateAction(getConfiguration());
    }

    private IslandMembershipAnemia toAnemia(IslandMembershipUpdateRequest request) {
        return new IslandMembershipAnemia(
                request.getIslandId().getId(),
                request.getIslandMemberId().getInternal(),
                request.isOwner());
    }

}
