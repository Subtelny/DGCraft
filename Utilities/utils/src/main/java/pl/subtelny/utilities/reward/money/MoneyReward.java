package pl.subtelny.utilities.reward.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.reward.Reward;

public class MoneyReward implements Reward {

    private final Economy economy;

    private final double money;

    public MoneyReward(Economy economy, double money) {
        this.economy = economy;
        this.money = money;
    }

    @Override
    public void admitReward(Player player) {
        economy.depositPlayer(player, money);
    }

}
