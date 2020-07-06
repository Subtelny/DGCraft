package pl.subtelny.utilities.condition.money;

import org.bukkit.entity.Player;
import pl.subtelny.utilities.condition.CostCondition;

public class MoneyCostCondition extends MoneyCondition implements CostCondition {

    public MoneyCostCondition(MoneyCondition moneyCondition) {
        super(moneyCondition.economy, moneyCondition.money);
    }

    @Override
    public void satisfyCondition(Player player) {
        economy.withdrawPlayer(player, money);
    }
}
