package pl.subtelny.gui.crate.inventory;

import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftInventoryCustom;
import org.bukkit.inventory.InventoryHolder;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.CrateId;
import java.util.Objects;

public class CraftCrateInventory extends CraftInventoryCustom implements CrateInventory {

    private final CrateId crateId;

    public CraftCrateInventory(InventoryHolder owner, int size, String title, CrateId crateId) {
        super(owner, size, title);
        this.crateId = crateId;
    }

    public static CraftCrateInventory create(CrateId crateId, int size, String title) {
        return new CraftCrateInventory(null, size, title, crateId);
    }

    public static CraftCrateInventory create(CrateId crateId, InventoryHolder owner, int size, String title) {
        return new CraftCrateInventory(owner, size, title, crateId);
    }

    @Override
    public CrateId getCrateId() {
        return crateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CraftCrateInventory that = (CraftCrateInventory) o;
        return Objects.equals(crateId, that.crateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), crateId);
    }
}
