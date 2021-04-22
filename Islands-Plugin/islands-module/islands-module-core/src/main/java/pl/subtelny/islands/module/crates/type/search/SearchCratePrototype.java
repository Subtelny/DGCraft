package pl.subtelny.islands.module.crates.type.search;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;
import java.util.Objects;

public class SearchCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("SEARCH");

    private final ItemStack searchItemStack;

    public SearchCratePrototype(CrateKey crateKey, String title, String permission, int size, Map<Integer, ItemCrate> content, ItemStack searchItemStack) {
        super(crateKey, TYPE, title, permission, size, content);
        this.searchItemStack = searchItemStack;
    }

    public ItemStack getSearchItemStack() {
        return searchItemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SearchCratePrototype that = (SearchCratePrototype) o;
        return Objects.equals(searchItemStack, that.searchItemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), searchItemStack);
    }
}
