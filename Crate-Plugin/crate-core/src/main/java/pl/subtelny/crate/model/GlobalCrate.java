package pl.subtelny.crate.model;

import org.bukkit.entity.Player;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.model.item.ItemCrateClickResult;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GlobalCrate extends AbstractCrate {

    private final Map<Integer, ItemCrate> items;

    protected GlobalCrate(CratePrototype prototype, Map<Integer, ItemCrate> items) {
        super(prototype);
        this.items = items;
    }

    @Override
    public boolean click(Player player, int slot) {
        Optional<ItemCrateClickResult> result = getItemCrateAtSlot(slot)
                .map(itemCrate -> itemCrate.click(player));
        result.ifPresent(this::throwConditions);
        return result
                .map(ItemCrateClickResult::isSuccessful)
                .orElse(false);
    }

    @Override
    protected Map<Integer, ItemCrate> getItems() {
        return new HashMap<>(items);
    }

    @Override
    protected Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return Optional.ofNullable(items.get(slot));
    }

    @Override
    protected void clearAll() {
        super.clearAll();
        items.clear();
    }

    private void throwConditions(ItemCrateClickResult itemCrateClickResult) {
        itemCrateClickResult
                .getNotSatisfiedConditions()
                .forEach(this::throwCondition);
    }

    private void throwCondition(pl.subtelny.utilities.condition.Condition condition) {
        MessageKey messageKey = condition.getMessageKey();
        throw ValidationException.of(messageKey.getKey(), messageKey.getObjects());
    }
}
