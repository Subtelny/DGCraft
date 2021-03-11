package pl.subtelny.islands.crate.type.invites.creator;

import com.google.common.collect.Queues;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.crate.api.*;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.api.type.personal.PersonalItemCrate;
import pl.subtelny.islands.crate.type.invites.InvitesCrateCreatorRequest;
import pl.subtelny.islands.crate.type.invites.InvitesCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.ColorUtil;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class InvitesCrateCreatorStrategy implements CrateCreatorStrategy<InvitesCrateCreatorRequest> {

    private final ConfirmationService confirmationService;

    @Autowired
    public InvitesCrateCreatorStrategy(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @Override
    public Crate create(InvitesCrateCreatorRequest request) {
        InventoryInfo inventory = createInventory(request);
        return new ContentCrate(
                request.getCrateKey(),
                request.getPermission(),
                inventory,
                prepareContent(request)
        );
    }

    private Map<Integer, ItemCrate> prepareContent(InvitesCrateCreatorRequest request) {
        Map<Integer, ItemCrate> content = new HashMap<>(request.getContent());
        List<ItemCrate> itemsToAdd = getInviteItemCrates(request.getInviteItemStack(), request.getIsland());
        List<Integer> freeSlots = getFreeSlots(content.keySet(), request.getInventorySize());
        ArrayDeque<Integer> freeSlotsQueue = Queues.newArrayDeque(freeSlots);
        itemsToAdd.stream()
                .filter(itemCrate -> !freeSlotsQueue.isEmpty())
                .forEach(itemCrate -> content.put(freeSlotsQueue.getFirst(), itemCrate));
        return content;
    }

    private List<Integer> getFreeSlots(Set<Integer> takenSlots, int invSize) {
        return IntStream.rangeClosed(0, invSize)
                .filter(slot -> !takenSlots.contains(slot))
                .boxed()
                .collect(Collectors.toList());
    }

    private List<ItemCrate> getInviteItemCrates(ItemStack itemStack, Island island) {
        AbstractIsland abstractIsland = (AbstractIsland) island;
        Map<IslandMember, ConfirmContextId> pendingRequests = abstractIsland.getPendingJoinRequests();
        return pendingRequests.entrySet().stream()
                .map(entry -> getInviteItemCrate(itemStack, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private ItemCrate getInviteItemCrate(ItemStack itemStack, IslandMember islandMember, ConfirmContextId confirmContextId) {
        Map<String, String> data = new HashMap<>();
        data.put("name", islandMember.getName());
        ItemStack prepareItemStack = ItemStackUtil.prepareItemStack(itemStack, data);
        InviteItemCrate inviteItemCrate = new InviteItemCrate(prepareItemStack, confirmationService, confirmContextId);
        return new PersonalItemCrate(IslandMessages.get(), inviteItemCrate);
    }

    private InventoryInfo createInventory(InvitesCrateCreatorRequest request) {
        return InventoryInfo.of(request.getTitle(), request.getInventorySize());
    }

    @Override
    public CrateType getType() {
        return InvitesCratePrototype.TYPE;
    }

    private static class InviteItemCrate implements ItemCrate {

        private final ItemStack itemStack;

        private final ConfirmationService confirmationService;

        private final ConfirmContextId confirmContextId;

        private InviteItemCrate(ItemStack itemStack,
                                ConfirmationService confirmationService,
                                ConfirmContextId confirmContextId) {
            this.itemStack = itemStack;
            this.confirmationService = confirmationService;
            this.confirmContextId = confirmContextId;
        }

        @Override
        public ItemCrateClickResult click(Player player) {
            confirmationService.confirm(player, confirmContextId);
            return ItemCrateClickResult.success(false);
        }

        @Override
        public ItemStack getItemStack() {
            return itemStack;
        }
    }

}
