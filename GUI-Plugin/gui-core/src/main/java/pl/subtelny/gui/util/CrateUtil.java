package pl.subtelny.gui.util;

import org.bukkit.inventory.Inventory;
import pl.subtelny.gui.crate.inventory.CraftCrateInventory;

public final class CrateUtil {

    public static boolean isCrateInventory(Inventory inventory) {
        return inventory instanceof CraftCrateInventory;
    }

}
