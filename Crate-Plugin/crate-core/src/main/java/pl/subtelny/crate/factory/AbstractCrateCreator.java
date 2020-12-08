package pl.subtelny.crate.factory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCrateCreator {

    ItemStack prepareItemStack(ItemStack itemStack, Map<String, String> data) {
        if (data.isEmpty()) {
            return itemStack;
        }
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore()
                        .stream()
                        .map(s -> replaceAllData(s, data))
                        .collect(Collectors.toList());
                itemMeta.setLore(lore);
            }

            if (itemMeta.hasDisplayName()) {
                String displayName = itemMeta.getDisplayName();
                displayName = replaceAllData(displayName, data);
                itemMeta.setDisplayName(displayName);
            }

            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private String replaceAllData(String value, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            value = value.replaceAll(entry.getKey(), entry.getValue());
        }
        return value;
    }

}
