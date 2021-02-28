package pl.subtelny.utilities.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.utilities.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ItemStackUtil {

    public static ItemStack addLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> newLore = new ArrayList<>();
        if (itemMeta.getLore() != null) {
            newLore = itemMeta.getLore();
        }
        newLore.addAll(lore);
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack prepareItemStack(ItemStack itemStack, Map<String, String> data) {
        if (data.isEmpty()) {
            return itemStack;
        }
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            List<String> lore = itemMeta.getLore();
            if (lore != null) {
                List<String> newLore = lore
                        .stream()
                        .map(line -> replaceWithData(line, data))
                        .map(ColorUtil::color)
                        .collect(Collectors.toList());
                itemMeta.setLore(newLore);
            }

            if (itemMeta.hasDisplayName()) {
                String displayName = itemMeta.getDisplayName();
                displayName = replaceWithData(displayName, data);
                itemMeta.setDisplayName(displayName);
            }

            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private static String replaceWithData(String value, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            value = value.replace(entry.getKey(), entry.getValue());
        }
        return value;
    }

}
