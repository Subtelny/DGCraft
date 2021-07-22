package pl.subtelny.islands.module.skyblock.component;

import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.confirmation.IslandConfirm;
import pl.subtelny.islands.api.events.DeletedIslandEvent;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.component.DeleteComponent;
import pl.subtelny.islands.event.IslandEventBus;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class SkyblockIslandDeleteComponent implements DeleteComponent<Islander, SkyblockIsland> {

    private final TransactionProvider transactionProvider;

    private final SkyblockIslandModule islandModule;

    private final SkyblockIslandRepository repository;

    private final SkyblockIslandOrganizer islandOrganizer;

    private final IslandMemberQueryService islandMemberQueryService;

    private final ConfirmationService confirmationService;

    public SkyblockIslandDeleteComponent(SkyblockIslandModule islandModule,
                                         TransactionProvider transactionProvider,
                                         SkyblockIslandRepository repository,
                                         SkyblockIslandOrganizer islandOrganizer,
                                         IslandMemberQueryService islandMemberQueryService,
                                         ConfirmationService confirmationService) {
        this.islandModule = islandModule;
        this.transactionProvider = transactionProvider;
        this.repository = repository;
        this.islandOrganizer = islandOrganizer;
        this.islandMemberQueryService = islandMemberQueryService;
        this.confirmationService = confirmationService;
    }

    @Override
    public void delete(Islander islandMember, IslandId islandId) {
        SkyblockIsland skyblockIsland = getSkyblockIsland(islandId);
        validateIslander(islandMember, skyblockIsland);
        sendConfirmation(islandMember, skyblockIsland);
    }

    private void sendConfirmation(Islander islander, SkyblockIsland island) {
        String contextIdRaw = String.join("@", "island.remove", islander.getName());
        String islandTypeName = island.getIslandType().getInternal();
        String title = IslandMessages.get().getFormattedMessage("skyblockIsland.delete.confirmation_title", islandTypeName);

        ConfirmationRequest request = ConfirmationRequest.builder(contextIdRaw, islander, new IslandConfirm(island))
                .stateListener(confirmationListener(islander, island.getId()))
                .title(title)
                .build();
        confirmationService.makeConfirmation(request);
    }

    private Callback<Boolean> confirmationListener(Islander islander, IslandId islandId) {
        return state -> {
            if (state) {
                deleteIsland(islander, islandId).whenComplete((unused, throwable) -> handleResult(islander, throwable));
            }
        };
    }

    private CompletableFuture<Void> deleteIsland(Islander islander, IslandId islandId) {
        SkyblockIsland skyblockIsland = getSkyblockIsland(islandId);
        leaveMembersFromIsland(skyblockIsland);
        IslandEventBus.call(new DeletedIslandEvent(skyblockIsland, islander));
        return transactionProvider.transactionAsync(() -> {
            repository.removeIsland(skyblockIsland);
            islandOrganizer.cleanIsland(skyblockIsland.getIslandCoordinates());
        }).toCompletableFuture();
    }

    private void handleResult(Islander islander, @Nullable Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof ValidationException) {
                String message = throwable.getMessage();
                IslandMessages.get().sendTo(islander, message, ((ValidationException) throwable).getValues());
            } else {
                IslandMessages.get().sendTo(islander, "skyblockIsland.delete.error");
                throwable.printStackTrace();
            }
        }
    }

    private void leaveMembersFromIsland(SkyblockIsland island) {
        islandMemberQueryService.getIslandMembers(island.getMembers())
                .forEach(island::leave);
    }

    private void validateIslander(Islander islandMember, SkyblockIsland skyblockIsland) {
        Validation.isTrue(skyblockIsland.isOwner(islandMember), "skyblockIsland.delete.islander_not_owner");
    }

    private SkyblockIsland getSkyblockIsland(IslandId islandId) {
        return islandModule.findIsland(islandId).orElseThrow(() -> ValidationException.of("skyblockIsland.delete.island_not_found"));
    }

}
