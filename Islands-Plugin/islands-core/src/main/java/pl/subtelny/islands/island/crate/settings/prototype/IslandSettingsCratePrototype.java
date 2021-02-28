package pl.subtelny.islands.island.crate.settings.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.islands.island.IslandType;

import java.util.Map;
import java.util.Objects;

public class IslandSettingsCratePrototype extends PageCratePrototype {

    public static final CrateType SETTINGS_CRATE_TYPE = new CrateType("ISLAND_SETTINGS");

    private static final int SETTINGS_INV_SIZE = 54;

    private static final String SETTINGS_INV_PERMISSION = "";

    private final IslandType islandType;

    public IslandSettingsCratePrototype(CrateId crateId,
                                        String title,
                                        Map<Integer, ItemCratePrototype> items,
                                        ItemStack previousPageItemStack,
                                        ItemStack nextPageItemStack,
                                        IslandType islandType,
                                        Map<Integer, ItemCratePrototype> staticItems) {
        super(crateId, SETTINGS_CRATE_TYPE, title, SETTINGS_INV_PERMISSION,
                SETTINGS_INV_SIZE, items, previousPageItemStack, nextPageItemStack, staticItems);
        this.islandType = islandType;
    }

    public IslandType getIslandType() {
        return islandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IslandSettingsCratePrototype that = (IslandSettingsCratePrototype) o;
        return Objects.equals(islandType, that.islandType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), islandType);
    }
}
