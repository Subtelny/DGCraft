package pl.subtelny.islands.island.crate.search.prototype;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.crate.search.condition.AskJoinIslandCondition;
import pl.subtelny.islands.island.crate.search.reward.AskJoinReward;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.item.ItemStackUtil;
import pl.subtelny.utilities.reward.Reward;

import java.util.*;
import java.util.stream.Collectors;

public class IslandSearchItemCratePrototypeFactory {

    private static final String NAME_PATTERN = "[NAME]";

    private static final String OWNER_PATTERN = "[OWNER]";

    private static final String DESC_PATTERN = "[DESC]";

    private static final String MEMBER_PATTERN = "[MEMBER-X]";

    private static final String MEMBER_STATUS_PATTERN = "[MEMBER-X-STATUS]";

    private static final String ON_VALUE = "&aON";

    private static final String OFF_VALUE = "&cOFF";

    private static final String NONE_VALUE = "BRAK";

    private final ItemStack sampleItem;

    private final Island island;

    private final ComponentProvider componentProvider;

    public IslandSearchItemCratePrototypeFactory(ItemStack sampleItem,
                                                 Island island,
                                                 ComponentProvider componentProvider) {
        this.sampleItem = sampleItem;
        this.island = island;
        this.componentProvider = componentProvider;
    }

    public ItemCratePrototype getItemCratePrototype() {
        List<OfflinePlayer> islanders = getIslanders();
        Map<String, String> data = getData(islanders);
        ItemStack preparedItemStack = getPreparedItemStack(data, islanders);
        return new ItemCratePrototype(preparedItemStack,
                false,
                true,
                getAskJoinConditions(),
                Collections.emptyList(),
                getAskJoinReward());
    }

    private List<OfflinePlayer> getIslanders() {
        return island.getMembers().stream()
                .filter(islandMemberId -> Islander.ISLAND_MEMBER_TYPE.equals(islandMemberId.getType()))
                .map(islandMemberId -> UUID.fromString(islandMemberId.getValue()))
                .map(Bukkit::getOfflinePlayer)
                .collect(Collectors.toList());
    }

    private List<Condition> getAskJoinConditions() {
        return Collections.singletonList(
                new AskJoinIslandCondition(componentProvider, island)
        );
    }

    private List<Reward> getAskJoinReward() {
        return Collections.singletonList(
                new AskJoinReward(componentProvider, island)
        );
    }

    private Map<String, String> getData(List<OfflinePlayer> islanders) {
        Map<String, String> data = new HashMap<>();
        data.put(NAME_PATTERN, island.getName());
        data.put(OWNER_PATTERN, getOwnerValue(islanders));
        data.put(DESC_PATTERN, getDesc());
        islanders.forEach(islander -> {
            String memberId = islander.getUniqueId().toString();
            data.put(MEMBER_PATTERN.replace("X", memberId), islander.getName());
            data.put(MEMBER_STATUS_PATTERN.replace("X", memberId), getStatus(islander));
        });
        return data;
    }

    private String getDesc() {
        return island.getConfiguration().findValue(new ConfigurationKey("SEARCH_MEMBERS_DESC"), String.class)
                .orElse(NONE_VALUE);
    }

    private String getOwnerValue(List<OfflinePlayer> islanders) {
        Optional<IslandMemberId> ownerIdOpt = island.getOwner();
        if (ownerIdOpt.isPresent()) {
            IslandMemberId ownerId = ownerIdOpt.get();
            Optional<OfflinePlayer> ownerOpt = islanders.stream()
                    .filter(islander -> islander.getUniqueId().toString().equals(ownerId.getValue()))
                    .findFirst();
            return ownerOpt
                    .map(OfflinePlayer::getName)
                    .orElse(NONE_VALUE);
        }
        return NONE_VALUE;
    }

    private ItemStack getPreparedItemStack(Map<String, String> data, List<OfflinePlayer> islanders) {
        ItemStack itemStack = sampleItem.clone();
        ItemStack preparedItemStack = prepareItemStack(itemStack, islanders);
        return ItemStackUtil.prepareItemStack(preparedItemStack, data);
    }

    private ItemStack prepareItemStack(ItemStack itemStack, List<OfflinePlayer> islanders) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();

            if (lore != null) {
                List<String> newLore = new ArrayList<>();
                lore.forEach(line -> {
                    if (line.contains(MEMBER_PATTERN)) {
                        islanders.forEach(islandMember -> {
                            String memberId = islandMember.getUniqueId().toString();
                            String member = MEMBER_PATTERN.replace("X", memberId);
                            String status = MEMBER_STATUS_PATTERN.replace("X", memberId);
                            String newLine = line.replace(MEMBER_PATTERN, member).replace(MEMBER_STATUS_PATTERN, status);
                            newLore.add(newLine);
                        });
                    } else {
                        newLore.add(line);
                    }
                });
                itemMeta.setLore(newLore);
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private String getStatus(OfflinePlayer offlinePlayer) {
        if (offlinePlayer.isOnline()) {
            return ON_VALUE;
        }
        return OFF_VALUE;
    }

}
