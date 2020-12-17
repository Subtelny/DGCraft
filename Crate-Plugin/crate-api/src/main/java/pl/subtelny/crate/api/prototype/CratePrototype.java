package pl.subtelny.crate.api.prototype;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CratePrototype {

    private final CrateId crateId;

    private final CrateType crateType;

    private final String title;

    private final String permission;

    private final int size;

    private final Map<Integer, ItemCratePrototype> items;

    public CratePrototype(CratePrototype cratePrototype) {
        this.crateId = cratePrototype.getCrateId();
        this.crateType = cratePrototype.getCrateType();
        this.title = cratePrototype.getTitle();
        this.permission = cratePrototype.getPermission();
        this.size = cratePrototype.getSize();
        this.items = new HashMap<>(cratePrototype.getItems());
    }

    public CratePrototype(CrateId crateId, CrateType crateType, String title, String permission, int size, Map<Integer, ItemCratePrototype> items) {
        this.crateId = crateId;
        this.crateType = crateType;
        this.title = title;
        this.permission = permission;
        this.size = size;
        this.items = items;
    }

    public CrateId getCrateId() {
        return crateId;
    }

    public CrateType getCrateType() {
        return crateType;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, ItemCratePrototype> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CratePrototype that = (CratePrototype) o;
        return size == that.size &&
                Objects.equals(crateId, that.crateId) &&
                Objects.equals(crateType, that.crateType) &&
                Objects.equals(title, that.title) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crateId, crateType, title, permission, size, items);
    }
}
