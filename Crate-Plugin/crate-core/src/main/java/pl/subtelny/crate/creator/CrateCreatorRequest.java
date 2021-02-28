package pl.subtelny.crate.creator;

import pl.subtelny.crate.CrateKey;
import pl.subtelny.crate.ItemCrate;

import java.util.Map;

public abstract class CrateCreatorRequest {

    private final CrateKey crateKey;

    private final Map<Integer, ItemCrate> content;

    private final String title;

    private final int inventorySize;

    private final String permission;

    protected CrateCreatorRequest(CrateKey crateKey, Map<Integer, ItemCrate> content, String title, int inventorySize, String permission) {
        this.crateKey = crateKey;
        this.content = content;
        this.title = title;
        this.inventorySize = inventorySize;
        this.permission = permission;
    }

    public CrateKey getCrateKey() {
        return crateKey;
    }

    public Map<Integer, ItemCrate> getContent() {
        return content;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }
}
