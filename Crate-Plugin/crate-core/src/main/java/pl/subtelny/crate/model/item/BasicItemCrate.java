package pl.subtelny.crate.model.item;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;

public class BasicItemCrate extends AbstractItemCrate {

    public BasicItemCrate(ItemStack itemStack,
                          ItemCratePrototype prototype) {
        super(itemStack, prototype);
    }

}
