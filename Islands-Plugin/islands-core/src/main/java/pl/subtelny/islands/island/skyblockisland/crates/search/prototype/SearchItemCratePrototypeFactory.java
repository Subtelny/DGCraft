package pl.subtelny.islands.island.skyblockisland.crates.search.prototype;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.skyblockisland.crates.search.SkyblockIslandSearchReward;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.item.ItemStackUtil;
import pl.subtelny.utilities.reward.Reward;

import java.util.*;
import java.util.stream.Collectors;

public class SearchItemCratePrototypeFactory {

    private final ItemStack sampleItem;

    private final Island island;

    private final IslanderQueryService islanderQueryService;

    public SearchItemCratePrototypeFactory(ItemStack sampleItem,
                                           Island island,
                                           IslanderQueryService islanderQueryService) {
        this.sampleItem = sampleItem;
        this.island = island;
        this.islanderQueryService = islanderQueryService;
    }

    public ItemCratePrototype getItemCratePrototype() {
        Map<String, String> data = getData();
        ItemStack preparedItemStack = getPreparedItemStack(data);
        return new ItemCratePrototype(preparedItemStack,
                false,
                true,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.singletonList(getReward()));
    }

    private Reward getReward() {
        return new SkyblockIslandSearchReward(islanderQueryService, island);
    }

    private Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("{OWNER}", getOwnerValue());
        data.put("{OWNER-STATUS}", getOwnerStatus());
        data.put("{DESC}", getDesc());
        island.getMembers().forEach(islandMember -> {
            data.put("{MEMBER-" + islandMember.getIslandMemberId() + "}", islandMember.getName());
            data.put("{MEMBER-" + islandMember.getIslandMemberId() + "-STATUS}", getStatus(islandMember));
        });
        return data;
    }

    private String getDesc() {
        return island.getConfiguration().getValue(new ConfigurationKey("SEARCH_MEMBERS_DESC"));
    }

    private String getOwnerValue() {
        return island.getOwner()
                .map(IslandMember::getName)
                .orElse("BRAK");
    }

    private String getOwnerStatus() {
        Boolean online = island.getOwner()
                .filter(islandMember -> islandMember.getIslandMemberId().getType().equals(Islander.ISLAND_MEMBER_TYPE))
                .map(islandMember -> ((Islander) islandMember).isOnline())
                .orElse(false);
        if (online) {
            return "&aON";
        }
        return "&cOFF";
    }

    private ItemStack getPreparedItemStack(Map<String, String> data) {
        ItemStack itemStack = sampleItem.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore != null) {
            List<String> newLore = new ArrayList<>();
            lore.forEach(s -> newLore.addAll(prepareLore(s)));
            itemMeta.setLore(newLore);
        }
        itemStack.setItemMeta(itemMeta);
        return ItemStackUtil.prepareItemStack(itemStack, data);
    }

    private List<String> prepareLore(String lore) {
        if (lore.contains("{MEMBER}")) {
            return island.getMembers().stream().map(islandMember -> lore
                    .replace("{MEMBER}", "{MEMBER-" + islandMember.getIslandMemberId() + "}")
                    .replace("{MEMBER-STATUS}", "{MEMBER-" + islandMember.getIslandMemberId() + "-STATUS}"))
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(lore);
    }

    private String getStatus(IslandMember islandMember) {
        boolean online = false;
        if (islandMember.getIslandMemberId().getType().equals(Islander.ISLAND_MEMBER_TYPE)) {
            online = ((Islander) islandMember).isOnline();
        }
        if (online) {
            return "&aON";
        }
        return "&cOFF";
    }

}
