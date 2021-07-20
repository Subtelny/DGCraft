package pl.subtelny.crate.inventory;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftInventoryCustom;
import pl.subtelny.crate.api.Crate;

import java.util.Objects;

public class CrateInventory extends CraftInventoryCustom {

    private final Crate crate;

    public CrateInventory(String title, int size, Crate crate) {
        super(null, size, title);
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
