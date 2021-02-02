package pl.subtelny.islands.island.crate.invites.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;

import java.util.Map;
import java.util.Objects;

public class IslandInvitesCratePrototype extends PageCratePrototype {

    public static final CrateType ISLAND_CRATE_TYPE = new CrateType("ISLAND_INVITES");

    private static final int SEARCH_INV_SIZE = 54;

    private static final String SEARCH_INV_PERMISSION = "";

    private final ItemStack inviteSampleItemStack;

    public IslandInvitesCratePrototype(CrateId crateId,
                                       String title,
                                       Map<Integer, ItemCratePrototype> items,
                                       ItemStack previousPageItemStack,
                                       ItemStack nextPageItemStack,
                                       Map<Integer, ItemCratePrototype> staticItems,
                                       ItemStack inviteSampleItemStack) {
        super(crateId, ISLAND_CRATE_TYPE, title, SEARCH_INV_PERMISSION, SEARCH_INV_SIZE, items,
                previousPageItemStack, nextPageItemStack, staticItems);
        this.inviteSampleItemStack = inviteSampleItemStack;
    }

    public ItemStack getInviteSampleItemStack() {
        return inviteSampleItemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IslandInvitesCratePrototype that = (IslandInvitesCratePrototype) o;
        return Objects.equals(inviteSampleItemStack, that.inviteSampleItemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), inviteSampleItemStack);
    }
}
