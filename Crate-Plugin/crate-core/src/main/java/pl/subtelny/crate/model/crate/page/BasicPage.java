package pl.subtelny.crate.model.crate.page;

import pl.subtelny.crate.model.item.ItemCrate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class BasicPage implements Page {

    private final Map<Integer, ItemCrate> items;

    public BasicPage(Map<Integer, ItemCrate> items) {
        this.items = items;
    }

    @Override
    public Map<Integer, ItemCrate> getItemCrates() {
        return new HashMap<>(items);
    }

    @Override
    public Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return Optional.ofNullable(items.get(slot));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicPage basicPage = (BasicPage) o;
        return Objects.equals(items, basicPage.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
