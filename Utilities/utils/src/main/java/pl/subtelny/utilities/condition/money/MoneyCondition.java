package pl.subtelny.utilities.condition.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

public class MoneyCondition implements Condition {

    protected final Economy economy;

    protected final double money;

    public MoneyCondition(Economy economy, double money) {
        this.economy = economy;
        this.money = money;
    }

    @Override
    public boolean satisfiesCondition(Player player) {
        double balance = economy.getBalance(player);
        return balance >= money;
    }

    @Override
    public MessageKey getMessageKey() {
        return new MessageKey("condition.money.not_satisfied", money);
    }
}
