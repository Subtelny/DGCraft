package pl.subtelny.crate.model.crate.page;

import pl.subtelny.crate.model.item.ItemCrate;

import java.util.Map;
import java.util.Optional;

public interface Page {

    Map<Integer, ItemCrate> getItemCrates();

    Optional<ItemCrate> getItemCrateAtSlot(int slot);

}
