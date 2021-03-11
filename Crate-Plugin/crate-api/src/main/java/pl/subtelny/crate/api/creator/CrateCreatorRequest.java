package pl.subtelny.crate.api.creator;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;

import java.util.Map;

public class CrateCreatorRequest {

    private final CrateType crateType;

    private final CrateKey crateKey;

    private final Map<Integer, ItemCrate> content;

    private final String title;

    private final int inventorySize;

    private final String permission;

    public CrateCreatorRequest(CrateType crateType, CrateKey crateKey, Map<Integer, ItemCrate> content, String title, int inventorySize, String permission) {
        this.crateType = crateType;
        this.crateKey = crateKey;
        this.content = content;
        this.title = title;
        this.inventorySize = inventorySize;
        this.permission = permission;
    }

    public CrateType getCrateType() {
        return crateType;
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
