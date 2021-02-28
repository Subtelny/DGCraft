package pl.subtelny.crate.type.global;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.ItemCrateClickResult;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;
import pl.subtelny.utilities.messages.Messages;

import java.util.List;
import java.util.Objects;

public class GlobalItemCrate implements ItemCrate {

    private final Messages messages;

    private final ItemCrate itemCrate;

    public GlobalItemCrate(Messages messages, ItemCrate itemCrate) {
        this.messages = messages;
        this.itemCrate = itemCrate;
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        ItemCrateClickResult clickResult = itemCrate.click(player);
        notifyPlayerAboutNotSatisfiedConditions(player, clickResult);
        return clickResult;
    }

    @Override
    public ItemStack getItemStack() {
        return itemCrate.getItemStack();
    }

    private void notifyPlayerAboutNotSatisfiedConditions(Player player, ItemCrateClickResult result) {
        List<Condition> notSatisfiedConditions = result.getNotSatisfiedConditions();
        notSatisfiedConditions.forEach(condition -> notifyPlayerNotSatisfiedCondition(player, condition));
    }

    private void notifyPlayerNotSatisfiedCondition(Player player, Condition condition) {
        MessageKey messageKey = condition.getMessageKey();
        messages.sendTo(player, messageKey.getKey(), messageKey.getObjects());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalItemCrate that = (GlobalItemCrate) o;
        return Objects.equals(itemCrate, that.itemCrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCrate);
    }
}
