package pl.subtelny.islands.island.cqrs.command;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.cqrs.IslandService;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.membership.IslandMembershipCommandService;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class IslandCommandService extends IslandService {

    private final TransactionProvider transactionProvider;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandMembershipCommandService islandMembershipCommandService;

    @Autowired
    public IslandCommandService(IslandModules islandModules,
                                TransactionProvider transactionProvider,
                                IslandMemberQueryService islandMemberQueryService,
                                IslandMembershipCommandService islandMembershipCommandService) {
        super(islandModules);
        this.transactionProvider = transactionProvider;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandMembershipCommandService = islandMembershipCommandService;
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

    public void membershipIsland(IslandMembershipRequest request) {
        IslandId islandId = request.getIslandId();
        IslandMember islandMember = request.getIslandMember();

        Island island = getIsland(islandId);
        island.leave(islandMember);

        IslandMembership member = IslandMembership.member(islandMember.getIslandMemberId(), islandId);
        if (request.isJoin()) {
            islandMembershipCommandService.saveIslandMembership(member);
        } else {
            islandMembershipCommandService.removeIslandMembership(member);
        }
    }

    private Island getIsland(IslandId islandId) {
        IslandModule<Island> islandModule = getIslandModule(islandId.getIslandType());
        return islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("islandCommand.island.not_found", islandId.getIslandType().getInternal()));
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

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return findIslandModule(islandType)
                .orElseThrow(() -> new IllegalStateException("Not found IslandModule for type " + islandType.getInternal()));
    }

}
