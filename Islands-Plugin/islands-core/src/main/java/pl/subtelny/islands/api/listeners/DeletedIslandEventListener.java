package pl.subtelny.islands.api.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.event.IslandEventListener;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.events.DeletedIslandEvent;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;
import pl.subtelny.islands.api.message.IslandMessages;

import java.util.List;

@Component
public class DeletedIslandEventListener extends IslandMemberIslandEventListener implements IslandEventListener<DeletedIslandEvent> {

    @Autowired
    public DeletedIslandEventListener(IslandMemberQueryService islandMemberQueryService) {
        super(islandMemberQueryService);
    }

    @Override
    public void handle(DeletedIslandEvent event) {
        IslandMember initiator = event.getInitiator();
        List<IslandMember> allExceptInitiator = getAllExceptIslandMember(event.getIsland().getMembers(), initiator);
        sendPersonalDeleteMessage(initiator);
        sendDeleteMessage(allExceptInitiator, initiator);
    }

    private void sendPersonalDeleteMessage(IslandMember islandMember) {
        String message = IslandMessages.get().getRawMessage("island.island_deleted");
        islandMember.sendMessage(message);
    }

    private void sendDeleteMessage(List<IslandMember> members, IslandMember initiator) {
        String message = IslandMessages.get().getFormattedMessage("island.member_deleted_island", initiator.getName());
        members.forEach(islandMember -> islandMember.sendMessage(message));
    }

}
