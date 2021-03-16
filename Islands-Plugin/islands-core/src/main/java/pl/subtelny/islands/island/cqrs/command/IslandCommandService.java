package pl.subtelny.islands.island.cqrs.command;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.cqrs.IslandService;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.utilities.Validation;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class IslandCommandService extends IslandService {

    private final TransactionProvider transactionProvider;

    private final IslandMemberQueryService islandMemberQueryService;

    @Autowired
    public IslandCommandService(IslandModules islandModules,
                                TransactionProvider transactionProvider,
                                IslandMemberQueryService islandMemberQueryService) {
        super(islandModules);
        this.transactionProvider = transactionProvider;
        this.islandMemberQueryService = islandMemberQueryService;
    }

    public CompletableFuture<Island> createIsland(IslandCreateRequest request) {
        validateIslandCreate(request);
        IslandModule<Island> islandModule = getIslandModule(request.getIslandType());
        return transactionProvider
                .transactionResultAsync(() -> islandModule.createIsland(request)).toCompletableFuture()
                .thenApply(island -> {
                    request.getOwner().ifPresent(member -> member.addIsland(island));
                    return island;
                });
    }

    public CompletableFuture<Object> removeIsland(Island island) {
        IslandModule<Island> islandModule = getIslandModule(island.getIslandType());
        leaveMembersFromIsland(island);
        return transactionProvider.transactionResultAsync(() -> {
            islandModule.removeIsland(island);
            return null;
        }).toCompletableFuture();
    }

    private void leaveMembersFromIsland(Island island) {
        List<IslandMember> islandMembers = islandMemberQueryService.getIslandMembers(island.getMembers());
        islandMembers.forEach(island::leave);
    }

    private void validateIslandCreate(IslandCreateRequest request) {
        Boolean hasIsland = request.getOwner().
                map(islandMember -> islandMember.hasIsland(request.getIslandType()))
                .orElse(false);
        Validation.isFalse(hasIsland, "islandCommand.createIsland.already_has_island", request.getIslandType());
    }

}
