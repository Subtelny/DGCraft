package pl.subtelny.gui.crate.model;

import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.model.ItemCrate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CratePrototype {

    private final CrateId crateId;

    private final Map<Integer, ItemCrate> items;

    private final boolean global;

    private final String title;

    private final int size;

    private final String permission;

    public CratePrototype(CrateId crateId, Map<Integer, ItemCrate> items, boolean global, String title, int size, String permission) {
        this.crateId = crateId;
        this.items = items;
        this.global = global;
        this.title = title;
        this.size = size;
        this.permission = permission;
    }

    public CrateId getCrateId() {
        return crateId;
    }

    public Map<Integer, ItemCrate> getItems() {
        return new HashMap<>(items);
    }

    public boolean isGlobal() {
        return global;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CratePrototype that = (CratePrototype) o;
        return global == that.global &&
                size == that.size &&
                Objects.equals(crateId, that.crateId) &&
                Objects.equals(items, that.items) &&
                Objects.equals(title, that.title) &&
                Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crateId, items, global, title, size, permission);
    }
}
