package pl.subtelny.utilities.reward.itemstack;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.reward.Reward;

public class ItemStackReward implements Reward {

    private final ItemStack itemStack;

    public ItemStackReward(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void admitReward(Player player) {
        player.getInventory().addItem(itemStack);
    }
}
