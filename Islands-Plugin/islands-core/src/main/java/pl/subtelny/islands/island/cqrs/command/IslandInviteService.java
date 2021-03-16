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
import pl.subtelny.islands.island.IslandConfiguration;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.confirmation.IslandConfirm;
import pl.subtelny.islands.island.confirmation.IslandMemberConfirm;
import pl.subtelny.islands.island.cqrs.query.IslandQueryService;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.membership.IslandMembershipService;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messageable;

import java.util.Optional;

@Component
public class IslandInviteService {

    private static final String ACCEPT_ASK_JOIN_REQUESTS_KEY = "ACCEPT_ASK_JOIN_REQUESTS";

    private final IslanderQueryService islanderQueryService;

    private final IslandQueryService islandQueryService;

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandMembershipService islandMembershipService;

    private final ConfirmationService confirmationService;

    @Autowired
    public IslandInviteService(IslanderQueryService islanderQueryService,
                               IslandQueryService islandQueryService,
                               IslandMemberQueryService islandMemberQueryService,
                               IslandMembershipService islandMembershipService,
                               ConfirmationService confirmationService) {
        this.islanderQueryService = islanderQueryService;
        this.islandQueryService = islandQueryService;
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandMembershipService = islandMembershipService;
        this.confirmationService = confirmationService;
    }

    public void invite(Player inviter, Player target) {
        Islander targetIslander = islanderQueryService.getIslander(target);
        Validation.isFalse(hasIsland(targetIslander), "islandInvite.target_already_have_island", targetIslander.getName());

        Islander inviterIslander = islanderQueryService.getIslander(inviter);
        Island inviterIsland = getIsland(inviterIslander)
                .orElseThrow(() -> ValidationException.of("islandInvite.inviter_not_have_island"));

        Validation.isTrue(inviterIsland.isOwner(inviterIslander), "islandInvite.inviter_not_have_permission_to_invite");
        sendInvite(inviterIsland, targetIslander);
    }

    private void sendInvite(Island targetIsland, IslandMember memberToJoin) {
        String rawContextId = getRawContextId(targetIsland, memberToJoin);
        Confirmable confirmable = new IslandMemberConfirm(memberToJoin);
        String title = IslandMessages.get().getFormattedMessage("islandInvite.send_invite_title", memberToJoin.getName());
        makeConfirmation(rawContextId, memberToJoin, confirmable, acceptInviteListener(targetIsland, memberToJoin), title);
    }

    private Callback<Boolean> acceptInviteListener(Island island, IslandMember islandMember) {
        return success -> {
            if (success) {
                Validation.isFalse(islandMember.hasIsland(island.getIslandType()), "islandInvite.target_already_have_island");
                islandMembershipService.join(island, islandMember);
            }
        };
    }

    public void ask(Player asker, Player target) {
        Islander askerIslander = islanderQueryService.getIslander(asker);
        Validation.isFalse(hasIsland(askerIslander), "islandInvite.asker_already_have_island", askerIslander.getName());

        Islander targetIslander = islanderQueryService.getIslander(target);
        Island targetIsland = getIsland(targetIslander)
                .orElseThrow(() -> ValidationException.of("islandInvite.target_not_have_island"));

        Validation.isTrue(acceptingJoinRequests(targetIsland), "islandInvite.island_join_request_disabled");
        sendAsk(targetIsland, askerIslander);
    }

    private void sendAsk(Island island, IslandMember asker) {
        String rawContextId = getRawContextId(island, asker);
        Confirmable confirmable = new IslandConfirm(island);
        String title = IslandMessages.get().getFormattedMessage("islandInvite.send_ask_title", asker.getName());
        Messageable messageable = island.getOwner()
                .flatMap(islandMemberQueryService::findIslandMember)
                .map(islandMember -> (Messageable) islandMember)
                .orElse(Messageable.EMPTY);

        ConfirmContextId contextId = makeConfirmation(rawContextId, messageable, confirmable, acceptAskListener(island, asker), title);
        island.addAskRequest(asker, contextId);
    }

    private Callback<Boolean> acceptAskListener(Island island, IslandMember islandMember) {
        return success -> {
            if (success) {
                Validation.isFalse(islandMember.hasIsland(island.getIslandType()), "islandInvite.asker_already_have_island");
                islandMembershipService.join(island, islandMember);
            }
        };
    }

    private ConfirmContextId makeConfirmation(String rawContextId,
                                              Messageable messageable,
                                              Confirmable confirmable,
                                              Callback<Boolean> listener,
                                              String title) {
        ConfirmationRequest request = ConfirmationRequest.builder(rawContextId, messageable, confirmable)
                .stateListener(listener)
                .title(title)
                .build();
        return confirmationService.makeConfirmation(request);
    }

    private boolean hasIsland(Islander islander) {
        return islander.hasIsland(IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE);
    }

    private Optional<Island> getIsland(Islander inviterIslander) {
        return inviterIslander.getIsland(IslandsConfiguration.ACTUAL_SEASON_ISLAND_TYPE)
                .flatMap(islandQueryService::findIsland);
    }

    private boolean acceptingJoinRequests(Island island) {
        IslandConfiguration configuration = island.getConfiguration();
        return configuration.getValue(ACCEPT_ASK_JOIN_REQUESTS_KEY, BooleanDataType.TYPE);
    }

    private String getRawContextId(Island island, IslandMember islandMember) {
        return String.join("@", "island.join", island.getId().getInternal(), islandMember.getName());
    }

}
