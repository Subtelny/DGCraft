package pl.subtelny.crate.api.prototype;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.CrateType;

import java.util.Map;

public class CratePrototype {

    private final CrateId crateId;

    private final CrateType crateType;

    private final String title;

    private final int size;

    private final Map<Integer, ItemCratePrototype> items;

    public CratePrototype(CrateId crateId, CrateType crateType, String title, int size, Map<Integer, ItemCratePrototype> items) {
        this.crateId = crateId;
        this.crateType = crateType;
        this.title = title;
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

    public int getSize() {
        return size;
    }

    public Map<Integer, ItemCratePrototype> getItems() {
        return items;
    }
}
