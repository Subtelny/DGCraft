package pl.subtelny.islands.island.skyblockisland.crates;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.islands.island.skyblockisland.module.SkyblockIslandModule;

import java.util.HashMap;
import java.util.Map;

public final class GetSkyblockCrateRequest extends GetCrateRequest {

    private final SkyblockIslandModule islandModule;

    public GetSkyblockCrateRequest(CrateId crateId, Map<String, String> data, SkyblockIslandModule islandModule) {
        super(crateId, data);
        this.islandModule = islandModule;
    }

    public SkyblockIslandModule getIslandModule() {
        return islandModule;
    }

    public static GetSkyblockCrateRequest of(CrateId crateId, SkyblockIslandModule islandModule) {
        return new GetSkyblockCrateRequest(crateId, new HashMap<>(), islandModule);
    }

}
