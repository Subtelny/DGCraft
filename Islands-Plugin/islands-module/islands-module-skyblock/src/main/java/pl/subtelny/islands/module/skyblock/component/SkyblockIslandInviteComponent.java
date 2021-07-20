package pl.subtelny.islands.module.skyblock.component;

import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.Confirmable;
import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.islands.api.*;
import pl.subtelny.islands.api.confirmation.IslandConfirm;
import pl.subtelny.islands.api.confirmation.IslanderConfirm;
import pl.subtelny.islands.api.membership.model.IslandMembership;
import pl.subtelny.islands.api.membership.repository.IslandMembershipRepository;
import pl.subtelny.islands.api.message.IslandMessages;
import pl.subtelny.islands.api.module.component.InviteComponent;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.Messageable;
import pl.subtelny.utilities.messages.PlayerMessageable;

import java.util.UUID;

public class SkyblockIslandInviteComponent implements InviteComponent<Islander, Islander, SkyblockIsland> {

    private static final String ACCEPT_ASK_JOIN_REQUESTS_KEY = "ACCEPT_ASK_JOIN_REQUESTS";

    private final SkyblockIslandModule islandModule;

    private final ConfirmationService confirmationService;

    private final IslandMembershipRepository islandMembershipRepository;

    public SkyblockIslandInviteComponent(SkyblockIslandModule islandModule, ConfirmationService confirmationService,
                                         IslandMembershipRepository islandMembershipRepository) {
        this.islandModule = islandModule;
        this.confirmationService = confirmationService;
        this.islandMembershipRepository = islandMembershipRepository;
    }

    @Override
    public void invite(Islander inviter, Islander target) {
        Validation.isFalse(target.hasIsland(islandModule.getIslandType()), "islandInvite.target_already_have_island", target.getName());

        SkyblockIsland inviterIsland = getIsland(inviter);
        Validation.isTrue(inviterIsland.isOwner(inviter), "islandInvite.inviter_not_have_permission_to_invite");

        sendInvite(inviterIsland, target);
        IslandMessages.get().sendTo(inviter, "islandInvite.invite_sent", target.getName());
    }

    @Override
    public void ask(Islander asker, Islander target) {
        Validation.isFalse(asker.hasIsland(islandModule.getIslandType()), "islandInvite.asker_already_have_island", target.getName());
        SkyblockIsland targetIsland = getIsland(target);
        ask(asker, targetIsland);
    }

    @Override
    public void ask(Islander asker, SkyblockIsland target) {
        Validation.isFalse(asker.hasIsland(islandModule.getIslandType()), "islandInvite.asker_already_have_island", target.getName());
        Validation.isTrue(acceptingJoinRequests(target), "islandInvite.island_join_request_disabled");
        sendAsk(target, asker);
        IslandMessages.get().sendTo(asker, "islandInvite.ask_sent", target.getName());
    }

    private SkyblockIsland getIsland(Islander islander) {
        IslandType islandType = islandModule.getIslandType();
        IslandId islandId = islander.getIsland(islandType)
                .orElseThrow(() -> ValidationException.of("inviteComponent.islander_not_have_island", islandType.getInternal()));
        return islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("inviteComponent.island_wrong_type", islandType.getInternal()));
    }

    private void sendAsk(Island island, Islander asker) {
        String rawContextId = getRawContextId(island, asker);
        Confirmable confirmable = new IslandConfirm(island);
        String title = IslandMessages.get().getFormattedMessage("islandInvite.send_ask_title", asker.getName());
        Messageable messageable = island.getOwner()
                .map(islandMemberId -> (Messageable) new PlayerMessageable(UUID.fromString(islandMemberId.getValue())))
                .orElse(Messageable.EMPTY);

        ConfirmContextId contextId = makeConfirmation(rawContextId, messageable, confirmable, acceptAskListener(island.getId(), asker), title);
        island.addAskRequest(asker, contextId);
    }

    private Callback<Boolean> acceptAskListener(IslandId islandId, IslandMember islandMember) {
        return success -> {
            if (success) {
                Validation.isFalse(islandMember.hasIsland(islandId.getIslandType()), "islandInvite.asker_already_have_island");
                join(islandId, islandMember);
            }
        };
    }

    private void sendInvite(SkyblockIsland island, Islander target) {
        String rawContextId = getRawContextId(island, target);
        Confirmable confirmable = new IslanderConfirm(target);
        String title = IslandMessages.get().getFormattedMessage("islandInvite.send_invite_title", target.getName());
        makeConfirmation(rawContextId, target, confirmable, acceptInviteListener(island.getId(), target), title);
    }

    private Callback<Boolean> acceptInviteListener(IslandId islandId, IslandMember islandMember) {
        return success -> {
            if (success) {
                Validation.isFalse(islandMember.hasIsland(islandId.getIslandType()), "islandInvite.target_already_have_island");
                join(islandId, islandMember);
            }
        };
    }

    private void join(IslandId islandId, IslandMember islandMember) {
        SkyblockIsland skyblockIsland = islandModule.findIsland(islandId)
                .orElseThrow(() -> ValidationException.of("inviteComponent.island_not_found", islandId.getId()));
        skyblockIsland.join(islandMember);

        IslandMembership member = IslandMembership.member(islandMember.getIslandMemberId(), skyblockIsland.getId());
        islandMembershipRepository.saveIslandMembership(member);
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

    private boolean acceptingJoinRequests(Island island) {
        IslandConfiguration configuration = island.getConfiguration();
        return configuration.getValue(ACCEPT_ASK_JOIN_REQUESTS_KEY);
    }

    private String getRawContextId(Island island, Islander target) {
        return String.join("@", "island.join", island.getId().getInternal(), target.getName());
    }
}
