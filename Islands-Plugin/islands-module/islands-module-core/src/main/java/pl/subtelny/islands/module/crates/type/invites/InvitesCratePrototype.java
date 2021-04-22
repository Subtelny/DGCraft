package pl.subtelny.islands.module.crates.type.invites;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Map;
import java.util.Objects;

public class InvitesCratePrototype extends CratePrototype {

    public final static CrateType TYPE = CrateType.of("INVITES");

    private final ItemStack inviteItemStack;

    public InvitesCratePrototype(CrateKey crateKey, String title, String permission, int size, Map<Integer, ItemCrate> content, ItemStack inviteItemStack) {
        super(crateKey, TYPE, title, permission, size, content);
        this.inviteItemStack = inviteItemStack;
    }

    public ItemStack getInviteItemStack() {
        return inviteItemStack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InvitesCratePrototype that = (InvitesCratePrototype) o;
        return Objects.equals(inviteItemStack, that.inviteItemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), inviteItemStack);
    }
}
