package pl.subtelny.islands.island.skyblockisland.crates.search;

import org.bukkit.inventory.ItemStack;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.islands.island.skyblockisland.crates.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.islands.island.skyblockisland.crates.search.prototype.SearchItemCratePrototypeFactory;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.message.IslandMessages;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SkyblockIslandSearchCrateCreator implements CrateCreator<IslandSearchCratePrototype> {

    private final ConfigurationKey SEARCH_MEMBERS = new ConfigurationKey("SEARCH_MEMBERS");

    private final IslandModules islandModules;

    private final CrateCreator<PageCratePrototype> crateCreator;

    private final IslanderQueryService islanderQueryService;

    private final IslandMessages messages;

    @Autowired
    public SkyblockIslandSearchCrateCreator(IslandModules islandModules,
                                            CrateCreator<PageCratePrototype> crateCreator,
                                            IslanderQueryService islanderQueryService,
                                            IslandMessages messages) {
        this.islandModules = islandModules;
        this.crateCreator = crateCreator;
        this.islanderQueryService = islanderQueryService;
        this.messages = messages;
    }

    @Override
    public Crate create(IslandSearchCratePrototype prototype, Map<String, String> data) {
        IslandModule<Island> islandModule = getIslandModule(prototype.getIslandType());
        Collection<Island> islands = getInviteOpenedIslands(islandModule);

        Map<Integer, ItemCratePrototype> itemCratePrototypes = getItemCratePrototypes(prototype, islands);
        itemCratePrototypes.putAll(prototype.getItems());

        PageCratePrototype pageCratePrototype = new PageCratePrototype(prototype.getCrateId(),
                prototype.getCrateType(),
                prototype.getTitle(),
                prototype.getPermission(),
                prototype.getSize(),
                itemCratePrototypes,
                prototype.getPreviousPageItemStack(),
                prototype.getNextPageItemStack());
        return crateCreator.create(pageCratePrototype, new HashMap<>());
    }

    @Override
    public CrateType getType() {
        return IslandSearchCratePrototype.SEARCH_CRATE_TYPE;
    }

    private Map<Integer, ItemCratePrototype> getItemCratePrototypes(IslandSearchCratePrototype prototype, Collection<Island> islands) {
        ItemStack searchSampleItemStack = prototype.getSearchSampleItemStack();
        List<ItemCratePrototype> prototypes = islands.stream()
                .map(island -> getSearchItemCratePrototype(searchSampleItemStack, island).getItemCratePrototype())
                .collect(Collectors.toList());

        Map<Integer, ItemCratePrototype> itemCratePrototypes = new HashMap<>();
        Iterator<ItemCratePrototype> iterator = prototypes.iterator();
        int slot = 0;
        while (iterator.hasNext()) {
            int page = (int) Math.ceil((double) (slot + 1) / prototype.getSize());
            int odd = page * 9;
            int toPlace = slot + odd;
            ItemCratePrototype item = iterator.next();
            itemCratePrototypes.put(toPlace, item);
            slot++;
        }
        return itemCratePrototypes;
    }

    private SearchItemCratePrototypeFactory getSearchItemCratePrototype(ItemStack searchSampleItemStack, Island island) {
        return new SearchItemCratePrototypeFactory(searchSampleItemStack, island, islanderQueryService, messages);
    }

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return islandModules.findIslandModule(islandType)
                .orElseThrow(() -> ValidationException.of("island-search.creator.island_module_not_found", islandType));
    }

    private Collection<Island> getInviteOpenedIslands(IslandModule<Island> islandModule) {
        Collection<Island> loadedIslands = islandModule.getAllLoadedIslands();
        return loadedIslands.stream()
                .filter(island -> island.getConfiguration().getValue(SEARCH_MEMBERS))
                .collect(Collectors.toList());
    }

}
