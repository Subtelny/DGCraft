package pl.subtelny.crate.api.type.personal;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateData;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.ItemCrateClickResult;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.item.ItemStackUtil;
import pl.subtelny.utilities.messages.Messages;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonalItemCrate implements ItemCrate {

    public final static CrateType TYPE = CrateType.of("PERSONAL");

    private final Messages messages;

    private final ItemCrate itemCrate;

    public PersonalItemCrate(Messages messages, ItemCrate itemCrate) {
        this.messages = messages;
        this.itemCrate = itemCrate;
    }

    @Override
    public ItemCrateClickResult click(Player player, CrateData crateData) {
        ItemCrateClickResult clickResult = itemCrate.click(player, crateData);
        return handleClickResult(clickResult);
    }

    @Override
    public ItemStack getItemStack() {
        return itemCrate.getItemStack();
    }

    private ItemCrateClickResult handleClickResult(ItemCrateClickResult clickResult) {
        if (clickResult.isSuccess() || clickResult.isRemoveItem()) {
            return clickResult;
        }
        List<Condition> notSatisfiedConditions = clickResult.getNotSatisfiedConditions();
        ItemStack newItemStack = prepareItemStackWithMessages(notSatisfiedConditions);
        return ItemCrateClickResult.failure(newItemStack, notSatisfiedConditions);
    }

    private ItemStack prepareItemStackWithMessages(List<Condition> notSatisfiedConditions) {
        List<String> messages = notSatisfiedConditions.stream()
                .map(Condition::getMessageKey)
                .map(messageKey -> this.messages.getColoredFormattedMessage(messageKey.getKey(), messageKey.getObjects()))
                .collect(Collectors.toList());

        ItemStack baseItemStack = getItemStack();
        addEmptyLineIfItemHasLore(messages, baseItemStack);
        return ItemStackUtil.addLore(baseItemStack, messages);
    }

    private void addEmptyLineIfItemHasLore(List<String> messages, ItemStack baseItemStack) {
        if (baseItemStack.getItemMeta().hasLore()) {
            messages.add("");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalItemCrate that = (PersonalItemCrate) o;
        return Objects.equals(itemCrate, that.itemCrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCrate);
    }
}
