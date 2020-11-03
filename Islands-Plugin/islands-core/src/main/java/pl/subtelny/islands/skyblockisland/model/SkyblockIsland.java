package pl.subtelny.islands.skyblockisland.model;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.skyblockisland.IslandExtendCalculator;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islandmember.IslandMemberQueryService;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;

public class SkyblockIsland extends AbstractIsland {

    private final IslandExtendCalculator extendCalculator;

    private final IslandCoordinates islandCoordinates;

    private int extendLevel;

    public SkyblockIsland(IslandExtendCalculator extendCalculator,
                          IslandMemberQueryService islandMemberQueryService,
                          IslandId islandId,
                          IslandType islandType,
                          LocalDateTime creationDate,
                          Cuboid cuboid,
                          Location spawn,
                          int points,
                          List<IslandMemberId> islandMemberIds,
                          IslandMemberId owner,
                          IslandCoordinates islandCoordinates,
                          int extendLevel) {
        super(islandMemberQueryService, islandId, islandType, creationDate, cuboid, spawn, points, islandMemberIds, owner);
        this.extendCalculator = extendCalculator;
        this.islandCoordinates = islandCoordinates;
        this.extendLevel = extendLevel;
    }

    @Override
    public void join(IslandMember member) {
        Validation.isTrue(Islander.ISLAND_MEMBER_TYPE.equals(member.getId().getType()), "skyblockIsland.cannot_join_not_islander");
        super.join(member);
    }

    @Override
    public World getWorld() {
        return cuboid.getWorld();
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public void extendIsland(int extendLevel) {
        Cuboid extendedCuboid = extendCalculator.calculateExtendedCuboid(getIslandCoordinates(), extendLevel);
        updateCuboid(extendedCuboid);
        this.extendLevel = extendLevel;
    }
}
