package pl.subtelny.crate;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryCustom;
import pl.subtelny.utilities.ColorUtil;

public class CrateInventory extends CraftInventoryCustom {

    private final Crate crate;

    public CrateInventory(int size, String title, Crate crate) {
        super(null, size, ColorUtil.color(title));
        this.crate = crate;
    }

    public Crate getCrate() {
        return crate;
    }

}
