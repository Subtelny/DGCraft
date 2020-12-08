package pl.subtelny.crate.model.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.condition.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalItemCrate extends AbstractItemCrate {

    private final CrateMessages messages;

    public PersonalItemCrate(ItemStack itemStack,
                             ItemCratePrototype prototype,
                             CrateMessages messages) {
        super(itemStack, prototype);
        this.messages = messages;
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        ItemCrateClickResult result = super.click(player);
        if (!result.isSuccessful()) {
            List<Condition> conditions = result.getNotSatisfiedConditions();
            ItemStack itemStack = prepareNotSatisfiedItemStack(conditions);
            return new ItemCrateClickResult(conditions, itemStack, false);
        }
        return result;
    }

    private ItemStack prepareNotSatisfiedItemStack(List<Condition> conditions) {
        ItemStack itemStack = getItemStack().clone();

        List<String> conditionsLore = conditions.stream()
                .map(Condition::getMessageKey)
                .map(messageKey -> {
                    String rawMessage = messages.getRawMessage(messageKey.getKey());
                    String message = String.format(rawMessage, messageKey.getObjects());
                    return String.format("   %s", message);
                })
                .collect(Collectors.toList());

        List<String> newLore = new ArrayList<>();
        List<String> currentLore = itemStack.getLore();
        if (currentLore != null) {
            newLore.addAll(currentLore);
        }
        newLore.add("");
        newLore.addAll(conditionsLore);
        itemStack.setLore(newLore);
        return itemStack;
    }

}
