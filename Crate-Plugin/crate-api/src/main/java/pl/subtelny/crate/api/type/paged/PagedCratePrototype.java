package pl.subtelny.crate.api.type.paged;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PagedCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("PAGED");

    private final ItemStack nextPageItemStack;

    private final ItemStack previousPageItemStack;

    private final Map<Integer, ItemCrate> staticContent;

    private final Set<ItemCrate> itemCratesToAdd = new HashSet<>();

    public PagedCratePrototype(CrateKey crateKey,
                               String title,
                               String permission,
                               int inventorySize,
                               Map<Integer, ItemCrate> content,
                               Map<Integer, ItemCrate> staticContent,
                               ItemStack nextPageItemStack,
                               ItemStack previousPageItemStack) {
        super(crateKey, TYPE, title, permission, inventorySize, content);
        this.staticContent = staticContent;
        this.nextPageItemStack = nextPageItemStack;
        this.previousPageItemStack = previousPageItemStack;
    }

    public ItemStack getNextPageItemStack() {
        return nextPageItemStack;
    }

    public ItemStack getPreviousPageItemStack() {
        return previousPageItemStack;
    }

    public Map<Integer, ItemCrate> getStaticContent() {
        return staticContent;
    }

    public Set<ItemCrate> getItemCratesToAdd() {
        return itemCratesToAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PagedCratePrototype that = (PagedCratePrototype) o;
        return Objects.equals(nextPageItemStack, that.nextPageItemStack) && Objects.equals(previousPageItemStack, that.previousPageItemStack) && Objects.equals(staticContent, that.staticContent) && Objects.equals(itemCratesToAdd, that.itemCratesToAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nextPageItemStack, previousPageItemStack, staticContent, itemCratesToAdd);
    }
}
