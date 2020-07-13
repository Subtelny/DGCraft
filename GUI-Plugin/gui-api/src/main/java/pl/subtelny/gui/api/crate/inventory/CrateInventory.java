package pl.subtelny.gui.api.crate.inventory;

import org.bukkit.inventory.Inventory;
import pl.subtelny.gui.api.crate.model.CrateId;

public interface CrateInventory extends Inventory {

    CrateId getCrateId();

}
