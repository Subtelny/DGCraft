package pl.subtelny.islands.island.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.event.IslandEventListener;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.events.IslandMemberJoinedIslandEvent;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.message.IslandMessages;

import java.util.List;

@Component
public class IslandMemberJoinedIslandEventListener extends IslandMemberIslandEventListener implements IslandEventListener<IslandMemberJoinedIslandEvent> {

    @Autowired
    public IslandMemberJoinedIslandEventListener(IslandMemberQueryService islandMemberQueryService) {
        super(islandMemberQueryService);
    }

    @Override
    public void handle(IslandMemberJoinedIslandEvent event) {
        IslandMember islandMember = event.getIslandMember();
        List<IslandMember> members = getAllExceptIslandMember(event.getIsland().getMembers(), islandMember);
        members.forEach(this::sendJoinedMessage);
        sendPersonalJoinMessage(islandMember);
    }

    private void sendPersonalJoinMessage(IslandMember islandMember) {
        String message = IslandMessages.get().getRawMessage("island.joined_island");
        islandMember.sendMessage(message);
    }

    private void sendJoinedMessage(IslandMember islandMember) {
        String message = IslandMessages.get().getFormattedMessage("island.member_joined_island", islandMember.getName());
        islandMember.sendMessage(message);
    }

}
