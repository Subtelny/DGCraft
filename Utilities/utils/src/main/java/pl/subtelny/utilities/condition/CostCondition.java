package pl.subtelny.utilities.condition;

import org.bukkit.entity.Player;

public interface CostCondition extends Condition {

    void satisfyCondition(Player player);

}
