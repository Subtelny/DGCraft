package pl.subtelny.crate;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryCustom;
import pl.subtelny.utilities.ColorUtil;

import java.util.Objects;

public class CrateInventory extends CraftInventoryCustom {

    private final Crate crate;

    public CrateInventory(int size, String title, Crate crate) {
        super(null, size, ColorUtil.color(title));
        this.crate = crate;
    }

    public Crate getCrate() {
        return crate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CrateInventory that = (CrateInventory) o;
        return Objects.equals(crate, that.crate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), crate);
    }
}
