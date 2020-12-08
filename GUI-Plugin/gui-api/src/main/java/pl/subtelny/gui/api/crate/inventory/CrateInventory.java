package pl.subtelny.gui.api.crate.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.model.CrateId;

public interface CrateInventory extends Inventory {

    void click(Player player, int slot);

}
