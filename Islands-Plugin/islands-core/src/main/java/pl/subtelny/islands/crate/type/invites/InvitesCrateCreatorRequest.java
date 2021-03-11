package pl.subtelny.islands.crate.type.invites;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.islands.island.Island;
import pl.subtelny.utilities.Validation;

import java.util.HashMap;
import java.util.Map;

public class InvitesCrateCreatorRequest extends CrateCreatorRequest {

    private final ItemStack inviteItemStack;

    private final Island island;

    protected InvitesCrateCreatorRequest(CrateKey crateKey,
                                         Map<Integer, ItemCrate> content,
                                         String title,
                                         int inventorySize,
                                         String permission,
                                         ItemStack inviteItemStack,
                                         Island island) {
        super(InvitesCratePrototype.TYPE, crateKey, content, title, inventorySize, permission);
        this.inviteItemStack = inviteItemStack;
        this.island = island;
    }

    public ItemStack getInviteItemStack() {
        return inviteItemStack;
    }

    public Island getIsland() {
        return island;
    }

    public static InvitesCrateCreatorRequest withIsland(InvitesCrateCreatorRequest request, Island island) {
        return new InvitesCrateCreatorRequest(request.getCrateKey(),
                request.getContent(),
                request.getTitle(),
                request.getInventorySize(),
                request.getPermission(),
                request.getInviteItemStack(),
                island);
    }

    public static Builder builder(CrateKey crateKey,
                                  int inventorySize,
                                  ItemStack invitesItemStack) {
        return new Builder(crateKey, inventorySize, invitesItemStack, null);
    }

    public static class Builder {

        private final CrateKey crateKey;

        private final Map<Integer, ItemCrate> content = new HashMap<>();

        private final int inventorySize;

        private final ItemStack inviteItemStack;

        private final Island island;

        private String title;

        private String permission;

        private Builder(CrateKey crateKey,
                        int inventorySize,
                        ItemStack inviteItemStack,
                        Island island) {
            this.crateKey = crateKey;
            this.inventorySize = inventorySize;
            this.inviteItemStack = inviteItemStack;
            this.island = island;
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

        public Builder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public InvitesCrateCreatorRequest build() {
            return new InvitesCrateCreatorRequest(crateKey, content, title, inventorySize, permission, inviteItemStack, island);
        }

    }

}
