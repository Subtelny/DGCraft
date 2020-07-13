package pl.subtelny.gui.api.crate.repository;

import org.bukkit.entity.Player;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;

public interface CrateInventoryCreator {

    CrateInventory createInv(Crate crate);

    CrateInventory createInv(Player player, Crate crate);

}
