package pl.subtelny.crate.model.crate.global;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateClickResult;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.model.crate.AbstractCrate;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.model.item.ItemCrate;
import pl.subtelny.crate.model.item.ItemCrateClickResult;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GlobalCrate extends AbstractCrate {

    public static CrateType GLOBAL_TYPE = new CrateType("GLOBAL");

    private final Map<Integer, ItemCrate> items;

    public GlobalCrate(CratePrototype prototype, Map<Integer, ItemCrate> items) {
        super(prototype);
        this.items = items;
    }

    @Override
    public void cleanIfNeeded() {
        //noop - global crates does not need to clean
    }

    @Override
    protected CrateClickResult click(Player player, ItemCrate itemCrate) {
        ItemCrateClickResult clickResult = itemCrate.click(player);
        handleNotSatisfiedClick(clickResult);
        return getCrateClickResult(itemCrate, clickResult);
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

    private void handleNotSatisfiedClick(ItemCrateClickResult itemCrateClickResult) {
        itemCrateClickResult
                .getNotSatisfiedConditions()
                .forEach(this::handleNotSatisfiedClick);
    }

    private void handleNotSatisfiedClick(Condition condition) {
        MessageKey messageKey = condition.getMessageKey();
        throw ValidationException.of(messageKey.getKey(), messageKey.getObjects());
    }
}
