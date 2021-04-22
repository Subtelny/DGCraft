package pl.subtelny.crate.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.click.ActionType;
import pl.subtelny.crate.CrateData;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.item.ItemStackUtil;
import pl.subtelny.utilities.messages.MessageKey;
import pl.subtelny.utilities.reward.Reward;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class BaseItemCrate implements ItemCrate {

    private final ItemStack itemStack;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    private final List<Reward> rewards;

    private final boolean closeAfterClick;

    public BaseItemCrate(ItemStack itemStack,
                         List<Condition> conditions,
                         List<CostCondition> costConditions,
                         List<Reward> rewards,
                         boolean closeAfterClick) {
        this.itemStack = itemStack;
        this.conditions = conditions;
        this.costConditions = costConditions;
        this.rewards = rewards;
        this.closeAfterClick = closeAfterClick;
    }

    @Override
    public ItemCrateClickResult click(Player player, ActionType actionType, CrateData crateData) {
        validateSatisfiedConditions(player);
        satisfyCostConditions(player);
        admitRewards(player);
        return getResult();
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    @Override
    public ItemStack getItemStack(Map<String, String> values) {
        ItemStack itemStack = getItemStack();
        return ItemStackUtil.prepareItemStack(itemStack, values);
    }

    private void validateSatisfiedConditions(Player player) {
        Optional<MessageKey> messageKeyOpt = Stream.concat(conditions.stream(), costConditions.stream())
                .filter(condition -> !condition.satisfiesCondition(player))
                .findFirst()
                .map(Condition::getMessageKey);
        if (messageKeyOpt.isPresent()) {
            MessageKey messageKey = messageKeyOpt.get();
            throw ValidationException.of(messageKey.getKey(), messageKey.getObjects());
        }
    }

    private void satisfyCostConditions(Player player) {
        costConditions.forEach(costCondition -> costCondition.satisfyCondition(player));
    }

    private void admitRewards(Player player) {
        rewards.forEach(reward -> reward.admitReward(player));
    }

    private ItemCrateClickResult getResult() {
        if (closeAfterClick) {
            return ItemCrateClickResult.CLOSE_CRATE;
        }
        return ItemCrateClickResult.SUCCESS;
    }

}
