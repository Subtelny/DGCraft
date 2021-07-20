package pl.subtelny.islands.api.cqrs.command;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.event.IslandEventBus;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.confirmation.IslandConfirm;
import pl.subtelny.islands.api.cqrs.IslandService;
import pl.subtelny.islands.api.events.DeletedIslandEvent;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class IslandDeleteService extends IslandService {

    private final TransactionProvider transactionProvider;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslanderQueryService islanderQueryService;

    private final ConfirmationService confirmationService;

    @Autowired
    public IslandDeleteService(IslandModules islandModules,
                               TransactionProvider transactionProvider,
                               IslandMemberQueryService islandMemberQueryService,
                               IslanderQueryService islanderQueryService,
                               ConfirmationService confirmationService) {
        super(islandModules);
        this.transactionProvider = transactionProvider;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islanderQueryService = islanderQueryService;
        this.confirmationService = confirmationService;
    }

    public CompletableFuture<Void> deleteIsland(Island island) {
        IslandModule<Island> islandModule = getIslandModule(island.getIslandType());
        leaveMembersFromIsland(island);
        return transactionProvider.transactionAsync(() -> islandModule.removeIsland(island)).toCompletableFuture();
    }

    public void deleteIsland(Player player, IslandType islandType) {
        Islander islander = islanderQueryService.getIslander(player);
        Island island = getIsland(islander, islandType);
        validatePermissions(islander, island);
        sendConfirmation(islander, island);
    }

    private void sendConfirmation(Islander islander, Island island) {
        String contextIdRaw = String.join("@", "island.remove", islander.getName());
        String islandTypeName = island.getIslandType().getInternal();
        String title = IslandMessages.get().getFormattedMessage("islandRemove.confirmation_title", islandTypeName);

        ConfirmationRequest request = ConfirmationRequest.builder(contextIdRaw, islander, new IslandConfirm(island))
                .stateListener(confirmationListener(islander, island.getId()))
                .title(title)
                .build();
        confirmationService.makeConfirmation(request);
    }

    private Callback<Boolean> confirmationListener(Islander islander, IslandId islandId) {
        return state -> {
            if (state) {
                deleteIsland(islander, islandId);
            }
        };
    }

    private void deleteIsland(Islander islander, IslandId islandId) {
        Island island = getIsland(islandId);
        IslandEventBus.call(new DeletedIslandEvent(island, islander));
        deleteIsland(island)
                .whenComplete((unused, throwable) -> handleResult(islander, throwable));
    }

    private void handleResult(Islander islander, @Nullable Throwable throwable) {
        if (throwable == null) {
            IslandMessages.get().sendTo(islander, "command.island.delete.island_removed");
        } else {
            if (throwable instanceof ValidationException) {
                String message = throwable.getMessage();
                IslandMessages.get().sendTo(islander, message, ((ValidationException) throwable).getValues());
            } else {
                IslandMessages.get().sendTo(islander, "command.island.delete.error");
                throwable.printStackTrace();
            }
        }
    }

    private void validatePermissions(IslandMember islandMember, Island island) {
        Validation.isTrue(island.isOwner(islandMember), "islandRemove.not_island_owner", islandMember.getName());
    }

    private void leaveMembersFromIsland(Island island) {
        List<IslandMember> islandMembers = islandMemberQueryService.getIslandMembers(island.getMembers());
        islandMembers.forEach(island::leave);
    }

}
