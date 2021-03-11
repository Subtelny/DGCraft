package pl.subtelny.crate.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class ContentCrate extends AbstractCrate {

    private final Map<Integer, ItemCrate> content;

    public ContentCrate(CrateKey crateKey, String permission, InventoryInfo inventory, Map<Integer, ItemCrate> content) {
        super(crateKey, permission, new HashSet<>(), inventory);
        this.content = content;
    }

    @Override
    public Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return Optional.ofNullable(content.get(slot));
    }

    @Override
    public Map<Integer, ItemCrate> getContent() {
        return new HashMap<>(content);
    }

    @Override
    public void addItemCrate(ItemCrate itemCrate) {
        int emptySlot = getFirstEmptySlot();
        content.put(emptySlot, itemCrate);
    }

    @Override
    public void removeItemCrate(int slot) {
        content.remove(slot);
    }

    private int getFirstEmptySlot() {
        return IntStream.rangeClosed(0, inventory.getSize())
                .filter(slot -> !content.containsKey(slot))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found empty slot"));
    }
}
