package pl.subtelny.islands.api.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.event.IslandEventListener;
import pl.subtelny.islands.api.IslandMember;
import pl.subtelny.islands.api.events.IslandMemberJoinedIslandEvent;
import pl.subtelny.islands.api.membership.IslandMemberQueryService;
import pl.subtelny.islands.api.message.IslandMessages;

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
        sendPersonalJoinMessage(islandMember);
        sendJoinedMessage(members, islandMember);
    }

    private void sendPersonalJoinMessage(IslandMember islandMember) {
        String message = IslandMessages.get().getRawMessage("island.joined_island");
        islandMember.sendMessage(message);
    }

    private void sendJoinedMessage(List<IslandMember> members, IslandMember joined) {
        String message = IslandMessages.get().getFormattedMessage("island.member_joined_island", joined.getName());
        members.forEach(islandMember -> islandMember.sendMessage(message));
    }

}
