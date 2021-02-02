package pl.subtelny.islands.island.crate.invites.prototype;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.crate.invites.reward.AcceptAskJoinReward;
import pl.subtelny.utilities.item.ItemStackUtil;
import pl.subtelny.utilities.reward.Reward;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IslandInviteItemCratePrototypeFactory {

    private static final String PLAYER_PATTERN = "[PLAYER]";

    private static final String TIME_PATTERN = "[TIME]";

    private final ItemStack sampleItemStack;

    private final Island island;

    private final IslandMember islandMember;

    private final Long time;

    public IslandInviteItemCratePrototypeFactory(ItemStack sampleItemStack,
                                                 Island island, IslandMember islandMember,
                                                 Long time) {
        this.sampleItemStack = sampleItemStack;
        this.island = island;
        this.islandMember = islandMember;
        this.time = time;
    }

    public ItemCratePrototype getItemCratePrototype() {
        ItemStack preparedItemStack = getPreparedItemStack();
        return new ItemCratePrototype(preparedItemStack,
                false,
                false,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.singletonList(getJoinReward()));
    }

    private Reward getJoinReward() {
        return new AcceptAskJoinReward(island, islandMember);
    }

    private ItemStack getPreparedItemStack() {
        return ItemStackUtil.prepareItemStack(sampleItemStack, getData());
    }

    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put(TIME_PATTERN, getFormattedDate());
        data.put(PLAYER_PATTERN, getPlayerName());
        return data;
    }

    private String getPlayerName() {
        return islandMember.getName();
    }

    private String getFormattedDate() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return DateTimeFormatter.ofPattern("HH:mm").format(localDateTime);
    }

}
