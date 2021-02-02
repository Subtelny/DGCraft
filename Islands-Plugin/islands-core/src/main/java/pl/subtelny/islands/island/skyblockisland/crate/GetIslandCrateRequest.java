package pl.subtelny.islands.island.skyblockisland.crate;

import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.query.request.GetCrateRequest;
import pl.subtelny.islands.island.Island;

import java.util.HashMap;
import java.util.Map;

public final class GetIslandCrateRequest extends GetCrateRequest {

    private final Island island;

    private GetIslandCrateRequest(CrateId crateId, Map<String, String> data) {
        super(crateId, data);
        this.island = null;
    }

    private GetIslandCrateRequest(CrateId crateId, Map<String, String> data, Island island) {
        super(crateId, data);
        this.island = island;
    }

    public Island getIsland() {
        return island;
    }

    public static GetIslandCrateRequest of(CrateId crateId) {
        return new GetIslandCrateRequest(crateId, new HashMap<>());
    }

    public static GetIslandCrateRequest of(CrateId crateId, Island island) {
        return new GetIslandCrateRequest(crateId, new HashMap<>(), island);
    }

}
