package pl.subtelny.crate.model.crate.page;

import pl.subtelny.crate.model.item.ItemCrate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class EmptyPage implements Page {

    @Override
    public Map<Integer, ItemCrate> getItemCrates() {
        return Collections.emptyMap();
    }

    @Override
    public Optional<ItemCrate> getItemCrateAtSlot(int slot) {
        return Optional.empty();
    }

}
