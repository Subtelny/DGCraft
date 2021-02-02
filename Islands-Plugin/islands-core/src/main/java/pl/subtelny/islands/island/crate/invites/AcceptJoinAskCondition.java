package pl.subtelny.islands.island.crate.invites;

import org.bukkit.entity.Player;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

public class AcceptJoinAskCondition implements Condition {

    private final IslandMember islandMember;

    public AcceptJoinAskCondition(IslandMember islandMember) {
        this.islandMember = islandMember;
    }

    @Override
    public boolean satisfiesCondition(Player player) {
        return false;
    }

    @Override
    public MessageKey getMessageKey() {
        return new MessageKey("condition.accept_join_ask.not_satisfied");
    }
}
