package pl.subtelny.islands.crate.type.invites;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;

public class InvitesCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("INVITES");

    private final ItemStack inviteItemStack;

    public InvitesCratePrototype(CrateKey crateKey, String title, String permission, int size, Map<Integer, ItemCrate> content, ItemStack inviteItemStack) {
        super(crateKey, TYPE, title, permission, size, content);
        this.inviteItemStack = inviteItemStack;
    }

    @Override
    public CrateCreatorRequest toCrateCreatorRequest() {
        return InvitesCrateCreatorRequest.builder(getCrateKey(), getSize(), inviteItemStack)
                .setPermission(getPermission())
                .setTitle(getTitle())
                .setContent(getContent())
                .build();
    }
}
