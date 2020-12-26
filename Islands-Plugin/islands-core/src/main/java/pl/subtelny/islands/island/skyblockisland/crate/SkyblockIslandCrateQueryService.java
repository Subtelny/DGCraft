package pl.subtelny.islands.island.skyblockisland.crate;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.query.CrateQueryService;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.islands.island.skyblockisland.crate.search.IslandSearchCrateCreator;
import pl.subtelny.islands.island.skyblockisland.crate.search.prototype.IslandSearchCratePrototype;
import pl.subtelny.islands.island.skyblockisland.crate.search.prototype.IslandSearchCratePrototypeFactory;
import pl.subtelny.islands.island.skyblockisland.module.SkyblockIslandModule;
import pl.subtelny.utilities.ConfigUtil;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@Component
public class SkyblockIslandCrateQueryService {

    private final IslandSearchCrateCreator searchCrateCreator;

    private final IslandSearchCratePrototypeFactory searchCratePrototypeFactory;

    private final CrateQueryService crateQueryService;

    @Autowired
    public SkyblockIslandCrateQueryService(IslandSearchCrateCreator searchCrateCreator,
                                           IslandSearchCratePrototypeFactory searchCratePrototypeFactory,
                                           CrateQueryService crateQueryService) {
        this.searchCrateCreator = searchCrateCreator;
        this.searchCratePrototypeFactory = searchCratePrototypeFactory;
        this.crateQueryService = crateQueryService;
    }

    public CratePrototype getCratePrototype(GetCratePrototypeRequest request) {
        if (isSkyblockIslandSearchCrateType(request.getFile())) {
            return searchCratePrototypeFactory.getCratePrototype(request);
        }
        return crateQueryService.getCratePrototype(request);
    }

    public Crate getCrate(GetSkyblockCrateRequest request) {
        return crateQueryService.findCratePrototype(request.getCrateId())
                .filter(this::isSkyblockIslandSearchCrateType)
                .map(cratePrototype -> createSearchCrate(cratePrototype, request.getData(), request.getIslandModule()))
                .orElseGet(() -> crateQueryService.getCrate(request));
    }

    private Crate createSearchCrate(CratePrototype cratePrototype, Map<String, String> data, SkyblockIslandModule islandModule) {
        return searchCrateCreator.create((IslandSearchCratePrototype) cratePrototype, data, islandModule);
    }

    private boolean isSkyblockIslandSearchCrateType(CratePrototype cratePrototype) {
        CrateType crateType = cratePrototype.getCrateType();
        return isSkyblockIslandSearchCrateType(crateType);
    }

    private boolean isSkyblockIslandSearchCrateType(File file) {
        Optional<String> type = ConfigUtil.getString(YamlConfiguration.loadConfiguration(file), "configuration.type");
        return type.map(CrateType::new)
                .map(this::isSkyblockIslandSearchCrateType)
                .orElse(false);
    }

    private boolean isSkyblockIslandSearchCrateType(CrateType crateType) {
        return IslandSearchCratePrototype.SEARCH_CRATE_TYPE.equals(crateType);
    }

}
