package pl.subtelny.islands.module.skyblock.crates.search;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateService;
import pl.subtelny.crate.api.creator.CrateCreateRequest;
import pl.subtelny.crate.api.item.ItemCrate;
import pl.subtelny.crate.api.item.ItemCrateLoadRequest;
import pl.subtelny.crate.api.item.ItemCrateLoader;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.prototype.CratePrototypeLoadGlobalRequest;
import pl.subtelny.crate.api.prototype.CratePrototypeLoadRequest;
import pl.subtelny.crate.api.prototype.CratePrototypeLoader;
import pl.subtelny.crate.api.prototype.paged.PagedCratePrototype;
import pl.subtelny.islands.api.IslandMemberId;
import pl.subtelny.islands.api.module.component.CratesComponent;
import pl.subtelny.islands.module.skyblock.SkyblockIslandModule;
import pl.subtelny.islands.module.skyblock.crates.ACrateManager;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;
import pl.subtelny.utilities.configuration.datatype.BooleanDataType;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.file.FileParserStrategy;
import pl.subtelny.utilities.item.ItemStackUtil;

import java.util.*;
import java.util.stream.Collectors;

public class SearchCrateManager extends ACrateManager {

    private static final String ACCEPT_ASK_JOIN_REQUESTS_KEY = "ACCEPT_ASK_JOIN_REQUESTS";

    private static final String SEARCH_FILE_NAME = "search.yml";

    private final SkyblockIslandModule islandModule;

    private final CrateService crateService;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final ItemCrateLoader itemCrateLoader;

    private PagedCratePrototype cratePrototype;

    private ItemCrate searchItem;

    public SearchCrateManager(
            SkyblockIslandModule islandModule,
            CrateService crateService,
            CratePrototypeLoader cratePrototypeLoader,
            ItemCrateLoader itemCrateLoader,
            CratesComponent islandCrates) {
        super(crateService, cratePrototypeLoader, islandModule.getIslandType().getInternal(), islandCrates);
        this.islandModule = islandModule;
        this.crateService = crateService;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.itemCrateLoader = itemCrateLoader;
    }

    @Override
    public void open(Player player) {
        validateOpen();

        List<ItemCrate> searchItems = getSearchItems();
        if (searchItems.isEmpty()) {
            throw ValidationException.of("skyblockIslandModule.crate.search.empty_opened_islands");
        }

        PagedCratePrototype prototype = new PagedCratePrototype(cratePrototype.getCrateId(),
                cratePrototype.getTitle(),
                cratePrototype.getStaticContent(),
                cratePrototype.getPageCratePrototypes(),
                searchItems,
                cratePrototype.getNextPageControllerItemCrate(),
                cratePrototype.getPreviousPageControllerItemCrate());
        Crate crate = crateService.createCrate(CrateCreateRequest.builder(prototype).build());
        crate.open(player);
    }

    @Override
    public void open(Player player, SkyblockIsland island) {
        open(player);
    }

    @Override
    public void reload() {
        cratePrototype = (PagedCratePrototype) loadCratePrototype(SEARCH_FILE_NAME);
        searchItem = loadSearchItem();
    }

    private List<ItemCrate> getSearchItems() {
        List<SkyblockIsland> islands = islandModule.getAllLoadedIslands().stream()
                .filter(island -> island.getConfiguration().getValue(ACCEPT_ASK_JOIN_REQUESTS_KEY))
                .collect(Collectors.toList());

        return islands.stream()
                .map(this::parseIsland)
                .collect(Collectors.toList());
    }

    private ItemCrate parseIsland(SkyblockIsland island) {
        return new SearchItemCrate(parseSearchItemStack(island), searchItem, island.getId());
    }

    private ItemStack parseSearchItemStack(SkyblockIsland island) {
        List<IslandMemberId> members = island.getMembers();

        ItemStack itemStack = searchItem.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            List<String> newLore = new ArrayList<>();
            itemMeta.getLore().forEach(lore -> {
                if (lore.contains("[MEMBER]")) {
                    members.forEach(islandMemberId -> {
                        OfflinePlayer player = getIslandMemberPlayer(islandMemberId);
                        String name = Optional.ofNullable(player.getName()).orElse("Brak");
                        String isOnline = player.isOnline() ? "&aON" : "&cOFF";
                        String rank = island.isOwner(islandMemberId) ? "Lider" : "";
                        newLore.add(
                                lore
                                        .replace("[MEMBER]", name)
                                        .replace("[MEMBER-STATUS]", isOnline)
                                        .replace("[MEMBER-RANK]", rank)
                        );
                    });
                } else {
                    newLore.add(lore);
                }
            });
            itemMeta.setLore(newLore);
            itemStack.setItemMeta(itemMeta);
        }

        Map<String, String> data = new HashMap<>();
        data.put("[NAME]", island.getName());
        data.put("[DESC]", "some desc");
        return ItemStackUtil.prepareItemStack(itemStack, data);
    }

    private OfflinePlayer getIslandMemberPlayer(IslandMemberId islandMemberId) {
        return Bukkit.getOfflinePlayer(UUID.fromString(islandMemberId.getValue()));
    }

    private ItemCrate loadSearchItem() {
        CratePrototypeLoadRequest request = prepareCratePrototypeBuilder(SEARCH_FILE_NAME).build();
        FileParserStrategy<ItemCrate> strategy = itemCrateLoader.getItemCrateFileParserStrategy(request.getItemCrateLoadRequest());
        return strategy.load("search-item");
    }

    @Override
    protected CratePrototype loadCratePrototype(String fileName) {
        CratePrototypeLoadRequest request = prepareCratePrototypeBuilder(SEARCH_FILE_NAME).build();
        ItemCrateLoadRequest itemCrateLoadRequest = request.getItemCrateLoadRequest();

        CratePrototypeLoadGlobalRequest build = CratePrototypeLoadGlobalRequest.builder(request.getFile(), PagedCratePrototype.TYPE)
                .addRewardParsers(itemCrateLoadRequest.getRewardParsers())
                .addCostConditionParsers(itemCrateLoadRequest.getCostConditionParsers())
                .addConditionParsers(itemCrateLoadRequest.getConditionParsers())
                .addItemCrateWrapperParserStrategies(itemCrateLoadRequest.getItemCrateWrapperParserStrategies())
                .build();
        return cratePrototypeLoader.loadCratePrototype(build);
    }

    private void validateOpen() {
        if (cratePrototype == null || searchItem == null) {
            throw ValidationException.of("skyblockIslandModule.crateManager.search.not_initialized_yet");
        }
    }

}
