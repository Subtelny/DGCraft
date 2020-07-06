package pl.subtelny.utilities.condition.itemstack;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

public class ItemStackCondition implements Condition {

    protected final ItemStack itemStack;

    public ItemStackCondition(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public boolean satisfiesCondition(Player player) {
        return player.getInventory().contains(itemStack);
    }

    @Override
    public MessageKey getMessageKey() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String name = itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : itemStack.getI18NDisplayName();
        return new MessageKey("condition.itemStack.not_satisfied", itemStack.getAmount(), name);
    }

}
