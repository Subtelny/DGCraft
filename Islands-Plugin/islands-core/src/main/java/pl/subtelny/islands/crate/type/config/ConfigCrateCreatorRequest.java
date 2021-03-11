package pl.subtelny.islands.crate.type.config;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.islands.island.Island;
import pl.subtelny.utilities.Validation;

import java.util.HashMap;
import java.util.Map;

public class ConfigCrateCreatorRequest extends CrateCreatorRequest {

    private final Island island;

    private final Map<Integer, ConfigItemCratePrototype> configContent;

    public ConfigCrateCreatorRequest(CrateKey crateKey,
                                     Map<Integer, ItemCrate> content,
                                     String title,
                                     int inventorySize,
                                     String permission,
                                     Island island,
                                     Map<Integer, ConfigItemCratePrototype> configContent) {
        super(ConfigCratePrototype.TYPE, crateKey, content, title, inventorySize, permission);
        this.island = island;
        this.configContent = configContent;
    }

    public Island getIsland() {
        return island;
    }

    public Map<Integer, ConfigItemCratePrototype> getConfigContent() {
        return configContent;
    }

    public static ConfigCrateCreatorRequest withIsland(ConfigCrateCreatorRequest request, Island island) {
        return new ConfigCrateCreatorRequest(
                request.getCrateKey(),
                request.getContent(),
                request.getTitle(),
                request.getInventorySize(),
                request.getPermission(),
                island,
                request.getConfigContent());
    }

    public static Builder builder(CrateKey crateKey, int inventorySize) {
        return new Builder(crateKey, inventorySize);
    }

    public static class Builder {

        private final CrateKey crateKey;

        private final int inventorySize;

        private final Map<Integer, ItemCrate> content = new HashMap<>();

        private final Map<Integer, ConfigItemCratePrototype> configContent = new HashMap<>();

        private String permission;

        private String title;

        private Island island;

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

        public Builder setConfigContent(Map<Integer, ConfigItemCratePrototype> configContent) {
            Validation.isTrue(configContent.keySet().stream().max(Integer::compare).orElse(0) < inventorySize, "Map contain item that not match invSize");
            Validation.isTrue(configContent.keySet().stream().min(Integer::compare).orElse(0) >= 0, "Map contain item with negative slot");
            this.configContent.clear();
            this.configContent.putAll(configContent);
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

        public Builder setIsland(Island island) {
            this.island = island;
            return this;
        }

        public ConfigCrateCreatorRequest build() {
            return new ConfigCrateCreatorRequest(crateKey, content, title, inventorySize, permission, island, configContent);
        }

    }
}
