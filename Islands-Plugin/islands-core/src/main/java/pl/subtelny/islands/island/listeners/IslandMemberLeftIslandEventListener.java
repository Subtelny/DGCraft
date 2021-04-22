package pl.subtelny.islands.island.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.event.IslandEventListener;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.events.IslandMemberLeftIslandEvent;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.islands.island.message.IslandMessages;

import java.util.List;

@Component
public class IslandMemberLeftIslandEventListener extends IslandMemberIslandEventListener implements IslandEventListener<IslandMemberLeftIslandEvent> {

    @Autowired
    public IslandMemberLeftIslandEventListener(IslandMemberQueryService islandMemberQueryService) {
        super(islandMemberQueryService);
    }

    @Override
    public void handle(IslandMemberLeftIslandEvent event) {
        IslandMember islandMember = event.getIslandMember();
        List<IslandMember> members = getAllExceptIslandMember(event.getIsland().getMembers(), islandMember);
        sendPersonalLeftMessage(islandMember);
        sendLeftMessage(members, islandMember);
    }

    private void sendPersonalLeftMessage(IslandMember islandMember) {
        String message = IslandMessages.get().getRawMessage("island.left_island");
        islandMember.sendMessage(message);
    }

    private void sendLeftMessage(List<IslandMember> members, IslandMember left) {
        String message = IslandMessages.get().getFormattedMessage("island.member_left_island", left.getName());
        members.forEach(islandMember -> islandMember.sendMessage(message));
    }

}
