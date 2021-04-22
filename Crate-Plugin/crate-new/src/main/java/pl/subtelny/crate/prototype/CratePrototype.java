package pl.subtelny.crate.prototype;

import pl.subtelny.crate.CrateId;
import pl.subtelny.crate.item.ItemCrate;

import java.util.Map;

public abstract class CratePrototype {

    private final CrateId crateId;

    private final int size;

    private final String title;

    private final String permission;

    private final Map<Integer, ItemCrate> itemCrates;

    private final boolean shared;

    public CratePrototype(CrateId crateId, int size, String title, String permission, Map<Integer, ItemCrate> itemCrates, boolean shared) {
        this.crateId = crateId;
        this.size = size;
        this.title = title;
        this.permission = permission;
        this.itemCrates = itemCrates;
        this.shared = shared;
    }

    public CrateId getCrateId() {
        return crateId;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }

    public Map<Integer, ItemCrate> getItemCrates() {
        return itemCrates;
    }

    public boolean isShared() {
        return shared;
    }
}
