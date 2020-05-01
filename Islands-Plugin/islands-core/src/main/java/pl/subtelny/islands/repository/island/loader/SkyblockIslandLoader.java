package pl.subtelny.islands.repository.island.loader;

import com.google.common.collect.Sets;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.List;
import java.util.Optional;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

    public SkyblockIslandLoader(Configuration configuration) {
        super(configuration);
    }

    public Optional<SkyblockIsland> loadIsland(SkyblockIslandLoadRequest request) {
        SkyblockIslandAnemiaLoadAction loader = new SkyblockIslandAnemiaLoadAction(configuration, request);
        return loadIsland(loader);
    }

    @Override
    protected SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia) {
        List<AccountId> members = loadIslandMembers(anemia.getIslandId());
        members.removeIf(accountId -> accountId.equals(anemia.getOwner()));

        Cuboid cuboid = SkyblockIslandUtil.buildExtendedCuboid(anemia.getIslandCoordinates(), anemia.getExtendLevel());
        IslandCoordinates islandCoordinates = anemia.getIslandCoordinates();
        AccountId owner = anemia.getOwner();
        int extendLevel = anemia.getExtendLevel();
        int points = anemia.getPoints();
        return new SkyblockIsland(cuboid, Sets.newHashSet(members), islandCoordinates, owner, extendLevel, points);
    }

    protected List<AccountId> loadIslandMembers(IslandId islandId) {
        return DSL.using(configuration)
                .select(Islanders.ISLANDERS.ID)
                .from(Islanders.ISLANDERS)
                .where(Islanders.ISLANDERS.SKYBLOCK_ISLAND.eq(islandId.getId()))
                .fetch(uuidRecord1 -> AccountId.of(uuidRecord1.component1()));
    }
}
