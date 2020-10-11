package pl.subtelny.gui.crate.model;

import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.utilities.Validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Crate {

    private final CrateId crateId;

    private final Map<Integer, ItemCrate> items;

    private final boolean global;

    private final String title;

    private final int size;

    private final String permission;

    public Crate(CrateId crateId, Map<Integer, ItemCrate> items, boolean global, String title, int size, String permission) {
        this.crateId = crateId;
        this.items = items;
        this.global = global;
        this.title = title;
        this.size = size;
        this.permission = permission;
    }

    public CrateId getId() {
        return crateId;
    }

    public Map<Integer, ItemCrate> getItems() {
        return new HashMap<>(items);
    }

    public Optional<ItemCrate> getItemAt(int slot) {
        return Optional.ofNullable(items.get(slot));
    }

    public void setItemAt(ItemCrate itemCrate, int slot) {
        Validation.isTrue(slot < size, "Cannot add itemCrate at slot " + slot + " to " + size + " slot size inventory");
        items.put(slot, itemCrate);
    }

    public boolean isGlobal() {
        return global;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public Optional<String> getPermission() {
        return Optional.ofNullable(permission);
    }
}
