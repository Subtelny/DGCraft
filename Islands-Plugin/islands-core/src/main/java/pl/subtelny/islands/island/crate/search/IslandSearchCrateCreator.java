package pl.subtelny.islands.island.crate.search;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.ComponentProvider;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.factory.CrateCreator;
import pl.subtelny.crate.api.prototype.ItemCratePrototype;
import pl.subtelny.crate.api.prototype.PageCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.crate.IslandCrateCreator;
import pl.subtelny.islands.island.crate.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.islands.island.module.IslandModule;
import pl.subtelny.islands.island.module.IslandModules;
import pl.subtelny.utilities.configuration.ConfigurationKey;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IslandSearchCrateCreator implements IslandCrateCreator<IslandSearchCratePrototype> {

    private final ConfigurationKey SEARCH_MEMBERS_ENABLED_KEY = new ConfigurationKey("SEARCH_MEMBERS_ENABLED");

    private final CrateCreator<PageCratePrototype> crateCreator;

    private final ComponentProvider componentProvider;

    @Autowired
    public IslandSearchCrateCreator(ComponentProvider componentProvider,
                                    CrateCreator<PageCratePrototype> crateCreator) {
        this.crateCreator = crateCreator;
        this.componentProvider = componentProvider;
    }

    @Override
    public Crate create(IslandSearchCratePrototype prototype,
                        Map<String, String> data,
                        Island island) {
        IslandModule<Island> islandModule = getIslandModule(prototype.getIslandType());
        Collection<Island> islands = getInviteOpenedIslands(islandModule);

        Map<Integer, ItemCratePrototype> itemCratePrototypes = new ItemSearchCrateSlotsCalculator(prototype, islands, componentProvider).getItemCrates();
        itemCratePrototypes.putAll(prototype.getItems());

        PageCratePrototype pageCratePrototype = new PageCratePrototype(prototype.getCrateId(),
                prototype.getCrateType(),
                prototype.getTitle(),
                prototype.getPermission(),
                prototype.getSize(),
                itemCratePrototypes,
                prototype.getPreviousPageItemStack(),
                prototype.getNextPageItemStack(),
                prototype.getStaticItems());
        return crateCreator.create(pageCratePrototype, data);
    }

    @Override
    public CrateType getType() {
        return IslandSearchCratePrototype.SEARCH_CRATE_TYPE;
    }

    private IslandModule<Island> getIslandModule(IslandType islandType) {
        return componentProvider.getComponent(IslandModules.class).findIslandModule(islandType)
                .orElseThrow(() -> ValidationException.of("crateCreator.island_module_not_found"));
    }

    private Collection<Island> getInviteOpenedIslands(IslandModule<? extends Island> islandModule) {
        Collection<? extends Island> loadedIslands = islandModule.getAllLoadedIslands();
        return loadedIslands.stream()
                .filter(island -> island.getConfiguration().findValue(SEARCH_MEMBERS_ENABLED_KEY, Boolean.class).orElse(false))
                .collect(Collectors.toList());
    }

}
