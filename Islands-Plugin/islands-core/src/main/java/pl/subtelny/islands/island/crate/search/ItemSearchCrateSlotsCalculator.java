package pl.subtelny.islands.island.crate.search;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.crate.ItemCrateSlotsCalculator;
import pl.subtelny.islands.island.crate.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.islands.island.crate.search.prototype.IslandSearchItemCratePrototypeFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemSearchCrateSlotsCalculator extends ItemCrateSlotsCalculator {

    private final IslandSearchCratePrototype cratePrototype;

    private final Collection<Island> islands;

    private final ComponentProvider componentProvider;

    public ItemSearchCrateSlotsCalculator(IslandSearchCratePrototype cratePrototype,
                                          Collection<Island> islands,
                                          ComponentProvider componentProvider) {
        super(cratePrototype);
        this.cratePrototype = cratePrototype;
        this.islands = islands;
        this.componentProvider = componentProvider;
    }

    public Map<Integer, ItemCratePrototype> getItemCrates() {
        List<ItemCratePrototype> islandItems = getIslandItemCratePrototypes();
        int pages = countNeededPagesForSlots(islandItems.size());
        return getItemCrates(islandItems, pages);
    }


    private List<ItemCratePrototype> getIslandItemCratePrototypes() {
        return islands.stream()
                .map(this::toIslandItemCratePrototype)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ItemCratePrototype toIslandItemCratePrototype(Island island) {
        ItemStack searchSampleItemStack = cratePrototype.getSearchSampleItemStack();
        return new IslandSearchItemCratePrototypeFactory(searchSampleItemStack, island, componentProvider)
                .getItemCratePrototype();
    }

}
