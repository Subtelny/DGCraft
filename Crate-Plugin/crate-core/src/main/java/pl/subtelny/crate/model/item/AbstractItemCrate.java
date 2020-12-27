package pl.subtelny.crate.model.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.utilities.condition.Condition;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractItemCrate implements ItemCrate {

    private final ItemStack itemStack;

    private final ItemCratePrototype prototype;

    public AbstractItemCrate(ItemStack itemStack,
                             ItemCratePrototype prototype) {
        this.itemStack = itemStack;
        this.prototype = prototype;
    }

    @Override
    public ItemCrateClickResult click(Player player) {
        List<Condition> notSatisfiedConditions = getNotSatisfiedConditions(player);
        if (notSatisfiedConditions.isEmpty()) {
            satisfyItemCrate(player);
            return ItemCrateClickResult.SUCCESSFUL;
        }
        return new ItemCrateClickResult(notSatisfiedConditions, null, false);
    }

    protected void satisfyItemCrate(Player player) {
        prototype.getCostConditions().forEach(costCondition -> costCondition.satisfyCondition(player));
        prototype.getRewards().forEach(reward -> reward.admitReward(player));
    }

    protected List<Condition> getNotSatisfiedConditions(Player player) {
        return Stream.of(prototype.getConditions(), prototype.getCostConditions())
                .flatMap(Collection::stream)
                .filter(condition -> !condition.satisfiesCondition(player))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isMovable() {
        return prototype.isMovable();
    }

    @Override
    public boolean isCloseAfterClick() {
        return prototype.isCloseAfterClick();
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
}
