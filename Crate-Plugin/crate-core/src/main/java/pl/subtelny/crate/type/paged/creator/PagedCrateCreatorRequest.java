package pl.subtelny.crate.type.paged.creator;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.ItemCrate;
import pl.subtelny.crate.creator.CrateCreatorRequest;
import pl.subtelny.crate.type.basic.creator.BasicCrateCreatorRequest;
import pl.subtelny.utilities.Validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PagedCrateCreatorRequest extends CrateCreatorRequest {

    private final Map<Integer, ItemCrate> staticItemCrates;

    private final Set<ItemCrate> itemCratesToAdd;

    private final ItemStack nextItemStack;

    private final ItemStack previousItemStack;

    private PagedCrateCreatorRequest(CrateKey crateKey,
                                     Map<Integer, ItemCrate> content,
                                     String title, int inventorySize,
                                     Map<Integer, ItemCrate> staticContent,
                                     Set<ItemCrate> itemCratesToAdd,
                                     ItemStack nextItemStack,
                                     ItemStack previousItemStack,
                                     String permission) {
        super(crateKey, content, title, inventorySize, permission);
        this.staticItemCrates = staticContent;
        this.itemCratesToAdd = itemCratesToAdd;
        this.nextItemStack = nextItemStack;
        this.previousItemStack = previousItemStack;
    }

    public Map<Integer, ItemCrate> getStaticItemCrates() {
        return staticItemCrates;
    }

    public Set<ItemCrate> getItemCratesToAdd() {
        return itemCratesToAdd;
    }

    public ItemStack getNextItemStack() {
        return nextItemStack;
    }

    public ItemStack getPreviousItemStack() {
        return previousItemStack;
    }

    public static Builder builder(CrateKey crateKey, int inventorySize, ItemStack nextItemStack, ItemStack previousItemStack) {
        return new Builder(crateKey, inventorySize, nextItemStack, previousItemStack);
    }

    public static class Builder {

        private final CrateKey crateKey;

        private final Map<Integer, ItemCrate> content = new HashMap<>();

        private final Map<Integer, ItemCrate> staticContent = new HashMap<>();

        private final Set<ItemCrate> itemCratesToAdd = new HashSet<>();

        private final int inventorySize;

        private final ItemStack nextItemStack;

        private final ItemStack previousItemStack;

        private String title;

        private String permission;

        private Builder(CrateKey crateKey, int inventorySize, ItemStack nextItemStack, ItemStack previousItemStack) {
            this.crateKey = crateKey;
            this.inventorySize = inventorySize;
            this.nextItemStack = nextItemStack;
            this.previousItemStack = previousItemStack;
        }

        public Builder setContent(Map<Integer, ItemCrate> content) {
            Validation.isTrue(content.keySet().stream().max(Integer::compare).orElse(0) < inventorySize, "Map contain item that not match invSize");
            Validation.isTrue(content.keySet().stream().min(Integer::compare).orElse(0) >= 0, "Map contain item with negative slot");
            this.content.clear();
            this.content.putAll(content);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder addItemCrate(ItemCrate itemCrate) {
            itemCratesToAdd.add(itemCrate);
            return this;
        }

        public Builder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder setStaticContent(Map<Integer, ItemCrate> staticContent) {
            Validation.isTrue(content.keySet().stream().max(Integer::compare).orElse(0) < inventorySize, "Map contain item that not match invSize");
            Validation.isTrue(content.keySet().stream().min(Integer::compare).orElse(0) >= 0, "Map contain item with negative slot");
            this.staticContent.clear();
            this.staticContent.putAll(staticContent);
            return this;
        }

        public PagedCrateCreatorRequest build() {
            return new PagedCrateCreatorRequest(crateKey, content, title, inventorySize, staticContent, itemCratesToAdd, nextItemStack, previousItemStack, permission);
        }

    }

}
