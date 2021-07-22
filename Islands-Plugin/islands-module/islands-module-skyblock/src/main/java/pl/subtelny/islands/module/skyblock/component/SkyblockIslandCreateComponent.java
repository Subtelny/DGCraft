package pl.subtelny.islands.module.skyblock.component;

import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.core.api.worldedit.OperationStatus;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.IslandModuleConfiguration;
import pl.subtelny.islands.api.module.component.CreateComponent;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.islands.module.skyblock.organizer.GenerateResult;
import pl.subtelny.islands.module.skyblock.organizer.SkyblockIslandOrganizer;
import pl.subtelny.islands.module.skyblock.repository.SkyblockIslandRepository;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.concurrent.CompletableFuture;

public class SkyblockIslandCreateComponent implements CreateComponent<Islander, SkyblockIsland> {

    private final SkyblockIslandModule islandModule;

    private final TransactionProvider transactionProvider;

    private final SkyblockIslandOrganizer islandOrganizer;

    private final SkyblockIslandRepository repository;

    public SkyblockIslandCreateComponent(SkyblockIslandModule islandModule,
                                         TransactionProvider transactionProvider,
                                         SkyblockIslandOrganizer islandOrganizer,
                                         SkyblockIslandRepository repository) {
        this.islandModule = islandModule;
        this.transactionProvider = transactionProvider;
        this.islandOrganizer = islandOrganizer;
        this.repository = repository;
    }

    @Override
    public CompletableFuture<SkyblockIsland> create(Islander islandMember) {
        validate(islandMember);
        return transactionProvider.transactionResultAsync(() -> proccessCreate(islandMember)).toCompletableFuture();
    }

    private void validate(Islander islandMember) {
        IslandModuleConfiguration configuration = islandModule.getConfiguration();
        Validation.isTrue(configuration.isCreateEnabled(), "skyblockIsland.create.create_island_disabled");
        Validation.isFalse(islandMember.hasIsland(islandModule.getIslandType()), "skyblockIsland.create.already_has_island");
    }

    private SkyblockIsland proccessCreate(Islander islandMember) {
        IslandId islandId = createIsland(islandMember);
        SkyblockIsland skyblockIsland = islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("skyblockIsland.create.not_found_island_after_create"));
        islandMember.addIsland(skyblockIsland);
        return skyblockIsland;
    }

    private IslandId createIsland(Islander owner) {
        try {
            GenerateResult result = islandOrganizer.generateIsland(getStatusCallback(owner));
            return repository.createIsland(result.getSpawn(), result.getCoordinates(), owner);
        } catch (ValidationException e) {
            throw ValidationException.of("Could not create island, coords returned to poll", e);
        }
    }

    private Callback<OperationStatus> getStatusCallback(Islander islander) {
        return operationStatus -> IslandMessages.get().sendTo(
                islander.getPlayer(),
                "skyblockIsland.create.creating_state_" + operationStatus.name().toLowerCase()
        );
    }

}
