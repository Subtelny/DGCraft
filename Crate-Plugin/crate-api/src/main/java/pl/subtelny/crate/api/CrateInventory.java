package pl.subtelny.crate.api;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.ColorUtil;

public class CrateInventory extends CraftInventoryCustom {

    private AbstractCrate crate;

    public CrateInventory(InventoryInfo inventoryInfo, AbstractCrate crate) {
        super(null, inventoryInfo.getSize(), ColorUtil.color(inventoryInfo.getTitle()));
        this.crate = crate;
    }

    public boolean isViewer(Player player) {
        return crate.isViewer(player);
    }

    public void close(Player player) {
        crate.removeViewer(player);
        if (!crate.hasViewers()) {
            crate = null;
        }
    }

    public Crate getCrate() {
        return crate;
    }

}
