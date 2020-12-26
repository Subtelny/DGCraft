package pl.subtelny.islands.island.skyblockisland.crate.search.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.islands.island.IslandType;

import java.util.Map;
import java.util.Objects;

public class IslandSearchCratePrototype extends PageCratePrototype {

    public static final CrateType SEARCH_CRATE_TYPE = new CrateType("ISLAND_SEARCH");

    private static final int SEARCH_INV_SIZE = 64;

    private static final String SEARCH_INV_PERMISSION = "";

    private final ItemStack searchSampleItemStack;

    private final IslandType islandType;

    public IslandSearchCratePrototype(CrateId crateId,
                                      String title,
                                      Map<Integer, ItemCratePrototype> items,
                                      ItemStack previousPageItemStack,
                                      ItemStack nextPageItemStack,
                                      ItemStack searchSampleItemStack,
                                      IslandType islandType) {
        super(crateId, SEARCH_CRATE_TYPE, title, SEARCH_INV_PERMISSION, SEARCH_INV_SIZE, items, previousPageItemStack, nextPageItemStack);
        this.searchSampleItemStack = searchSampleItemStack;
        this.islandType = islandType;
    }

    public ItemStack getSearchSampleItemStack() {
        return searchSampleItemStack;
    }

    public IslandType getIslandType() {
        return islandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IslandSearchCratePrototype that = (IslandSearchCratePrototype) o;
        return Objects.equals(searchSampleItemStack, that.searchSampleItemStack) &&
                Objects.equals(islandType, that.islandType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), searchSampleItemStack, islandType);
    }
}
