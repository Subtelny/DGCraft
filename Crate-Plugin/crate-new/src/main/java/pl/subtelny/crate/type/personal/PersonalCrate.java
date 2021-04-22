package pl.subtelny.crate.type.personal;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.crate.BaseCrate;
import pl.subtelny.crate.CrateData;
import pl.subtelny.crate.CrateId;
import pl.subtelny.crate.click.CrateClickResult;
import pl.subtelny.crate.item.ItemCrate;
import pl.subtelny.crate.item.ItemCrateClickResult;
import pl.subtelny.crate.messages.CrateMessages;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonalCrate extends BaseCrate {

    private static final String LORE_SEPARATOR = " ";

    public PersonalCrate(CrateId crateId,
                         Inventory inventory,
                         CrateData crateData,
                         List<Condition> useConditions,
                         Map<Integer, ItemCrate> itemCrates) {
        super(crateId, inventory, crateData, useConditions, itemCrates, false);
    }

    @Override
    protected CrateClickResult handleClickResult(Player player, int slot, ItemCrate itemCrate, ItemCrateClickResult clickResult) {
        if (!clickResult.isSuccess()) {
            ItemStack itemStack = getItemStack(itemCrate);
            ItemStack preparedItemStack = fillNotSatisfiedConditionMessages(itemStack, clickResult);
            setItem(slot, preparedItemStack);
            return CrateClickResult.CANT_USE;
        }
        return super.handleClickResult(player, slot, itemCrate, clickResult);
    }

    private ItemStack fillNotSatisfiedConditionMessages(ItemStack itemStack, ItemCrateClickResult clickResult) {
        List<Condition> notSatisfiedConditions = clickResult.getNotSatisfiedConditions();
        if (notSatisfiedConditions.isEmpty()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = getLore(itemMeta);
            List<String> notSatisfiedMessages = getNotSatisfiedMessages(notSatisfiedConditions);

            lore.add(LORE_SEPARATOR);
            lore.addAll(notSatisfiedMessages);
            itemMeta.setLore(lore);
        }
        return itemStack;
    }

    private List<String> getNotSatisfiedMessages(List<Condition> notSatisfiedConditions) {
        Messages messages = getCrateData().<Messages>findData("messages")
                .orElseGet(CrateMessages::get);
        return notSatisfiedConditions
                .stream()
                .map(Condition::getMessageKey)
                .map(messageKey -> messages.getFormattedMessage(messageKey.getKey(), messageKey.getObjects()))
                .collect(Collectors.toList());
    }

    private List<String> getLore(ItemMeta itemMeta) {
        List<String> lore = new ArrayList<>();
        List<String> itemMetaLore = itemMeta.getLore();
        if (itemMetaLore != null) {
            lore.addAll(itemMetaLore);
        }
        return lore;
    }

}
