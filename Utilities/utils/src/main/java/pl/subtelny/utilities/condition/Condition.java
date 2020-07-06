package pl.subtelny.utilities.condition;

import org.bukkit.entity.Player;
import pl.subtelny.utilities.messages.MessageKey;

public interface Condition {

    boolean satisfiesCondition(Player player);

    MessageKey getMessageKey();

}
