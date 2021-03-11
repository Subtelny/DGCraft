package pl.subtelny.islands.island.cqrs.command;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.Confirmable;
import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.confirmation.IslandConfirmable;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.exception.ValidationException;

@Component
public class IslandInviteService {

    private static final ConfigurationKey ACCEPTING_JOIN = new ConfigurationKey("ACCEPTING_JOIN_REQUESTS");

    private final IslanderQueryService islanderQueryService;

    private final IslandQueryService islandQueryService;

    private final ConfirmationService confirmationService;

    @Autowired
    public IslandInviteService(IslanderQueryService islanderQueryService,
                               IslandQueryService islandQueryService,
                               ConfirmationService confirmationService) {
        this.islanderQueryService = islanderQueryService;
        this.islandQueryService = islandQueryService;
        this.confirmationService = confirmationService;
    }

    public void askJoinToPlayerIsland(Player player, Player target) {
        Island targetIsland = getIsland(target);
        askJoinToIsland(player, targetIsland);
    }

    private void askJoinToIsland(Player player, Island island) {
        Validation.isTrue(acceptingJoinRequests(island), "islandInvite.island_join_request_disabled");
        Islander islander = islanderQueryService.getIslander(player);
        validateAskJoin(island, islander);
        askJoin(island, islander);
    }

    private Island getIsland(Player player) {
        Islander targetIslander = islanderQueryService.getIslander(player);
        return targetIslander.getIsland(IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE)
                .flatMap(islandQueryService::findIsland)
                .orElseThrow(() -> ValidationException.of("islandInvite.target_not_have_island"));
    }

    private boolean acceptingJoinRequests(Island island) {
        Configuration configuration = island.getConfiguration();
        return configuration.findValue(ACCEPTING_JOIN, Boolean.class).orElse(false);
    }

    private void askJoin(Island island, Islander islander) {
        String rawContextId = getRawContextId(island, islander);
        Confirmable confirmable = new IslandConfirmable(island);
        ConfirmationRequest request = ConfirmationRequest.builder(rawContextId, islander.getPlayer(), confirmable)
                .stateListener(joinStateListener(island, islander))
                .build();
        ConfirmContextId confirmContextId = confirmationService.makeConfirmation(request);
        ((AbstractIsland) island).addPendingJoinRequest(islander, confirmContextId);
    }

    private Callback<Boolean> joinStateListener(Island island, Islander islander) {
        return success -> {
            if (success) {
                join(island, islander);
            }
        };
    }

    private void validateAskJoin(Island island, Islander islander) {
        Validation.isFalse(islander.hasIsland(island.getIslandType()), "islandInvite.islander_already_has_island_type");
    }

    private void join(Island island, Islander islander) {
        validateAskJoin(island, islander);
        island.join(islander);
    }

    private String getRawContextId(Island island, Islander islander) {
        return String.join("@", "island.join", island.getId().getInternal(), islander.getName());
    }

}
