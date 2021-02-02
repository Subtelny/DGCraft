package pl.subtelny.islands.island.crate.invites;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.crate.ItemCrateSlotsCalculator;
import pl.subtelny.islands.island.crate.invites.prototype.IslandInviteItemCratePrototypeFactory;
import pl.subtelny.islands.island.crate.invites.prototype.IslandInvitesCratePrototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemInviteCrateSlotsCalculator extends ItemCrateSlotsCalculator {

    private final IslandInvitesCratePrototype cratePrototype;

    private final Island island;

    public ItemInviteCrateSlotsCalculator(IslandInvitesCratePrototype cratePrototype,
                                          Island island) {
        super(cratePrototype);
        this.cratePrototype = cratePrototype;
        this.island = island;
    }

    public Map<Integer, ItemCratePrototype> getItemCrates() {
        List<ItemCratePrototype> islandItems = getInviteItemCratePrototypes();
        int pages = countNeededPagesForSlots(islandItems.size());
        return getItemCrates(islandItems, pages);
    }

    private List<ItemCratePrototype> getInviteItemCratePrototypes() {
        Map<IslandMember, Long> pendingJoinRequests = island.getPendingJoinRequests();
        return pendingJoinRequests.entrySet().stream()
                .map(entry -> toInviteItemCratePrototype(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ItemCratePrototype toInviteItemCratePrototype(IslandMember islandMember, Long time) {
        ItemStack sampleItemStack = cratePrototype.getInviteSampleItemStack();
        return new IslandInviteItemCratePrototypeFactory(sampleItemStack, island, islandMember, time)
                .getItemCratePrototype();
    }
}
