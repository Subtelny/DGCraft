package pl.subtelny.crate.api.type.basic.creator;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.utilities.Validation;

import java.util.HashMap;
import java.util.Map;

public class BasicCrateCreatorRequest extends CrateCreatorRequest {

    public static CrateType TYPE = CrateType.of("BASIC");

    public BasicCrateCreatorRequest(CrateKey crateKey, Map<Integer, ItemCrate> itemCrates, String title, int inventorySize, String permission) {
        super(TYPE, crateKey, itemCrates, title, inventorySize, permission);
    }

    public static Builder builder(CrateKey crateKey, int inventorySize) {
        return new Builder(crateKey, inventorySize);
    }

    public static class Builder {

        private final CrateKey crateKey;

        private final int inventorySize;

        private final Map<Integer, ItemCrate> content = new HashMap<>();

        private String permission;

        private String title;

        public Builder(CrateKey crateKey, int inventorySize) {
            this.crateKey = crateKey;
            this.inventorySize = inventorySize;
        }

        public Builder setItemCrate(int slot, ItemCrate itemCrate) {
            Validation.isTrue(slot >= 0, "Slot cannot be less than 0");
            Validation.isTrue(slot < inventorySize, "Slot cannot be equal or higher than invSize");
            content.put(slot, itemCrate);
            return this;
        }

        public Builder setContent(Map<Integer, ItemCrate> content) {
            Validation.isTrue(content.keySet().stream().max(Integer::compare).orElse(0) < inventorySize, "Map contain item that not match invSize");
            Validation.isTrue(content.keySet().stream().min(Integer::compare).orElse(0) >= 0, "Map contain item with negative slot");
            this.content.clear();
            this.content.putAll(content);
            return this;
        }

        public Builder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BasicCrateCreatorRequest build() {
            return new BasicCrateCreatorRequest(crateKey, content, title, inventorySize, permission);
        }

    }

}
