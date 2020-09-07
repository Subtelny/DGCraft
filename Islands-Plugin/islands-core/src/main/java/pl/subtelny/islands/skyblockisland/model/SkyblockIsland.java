package pl.subtelny.islands.skyblockisland.model;

import org.bukkit.Location;
import pl.subtelny.groups.api.GroupsContext;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.model.AbstractIsland;
import pl.subtelny.islands.island.model.AbstractIslandMember;
import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.Optional;

public class SkyblockIsland extends AbstractIsland {

    private final IslandCoordinates islandCoordinates;

    private int extendLevel;

    public SkyblockIsland(SkyblockIslandId islandId,
                          Location spawn,
                          LocalDateTime createdDate,
                          Cuboid cuboid,
                          GroupsContext groupsContext,
                          IslandCoordinates islandCoordinates,
                          int points,
                          int extendLevel) {
        super(islandId, createdDate, groupsContext, cuboid, spawn, points);
        this.islandCoordinates = islandCoordinates;
        this.extendLevel = extendLevel;
    }

    public SkyblockIsland(SkyblockIslandId islandId, LocalDateTime createdDate, GroupsContext groupsContext, Location spawn, Cuboid cuboid, IslandCoordinates islandCoordinates) {
        super(islandId, createdDate, groupsContext, cuboid, spawn);
        this.islandCoordinates = islandCoordinates;
    }

    public SkyblockIsland(SkyblockIslandId islandId, GroupsContext groupsContext, Location spawn, Cuboid cuboid, IslandCoordinates islandCoordinates) {
        this(islandId, LocalDateTime.now(), groupsContext, spawn, cuboid, islandCoordinates);
    }

    public SkyblockIsland(Location spawn, Cuboid cuboid, IslandCoordinates islandCoordinates) {
        this(null, LocalDateTime.now(), null, spawn, cuboid, islandCoordinates);
    }

    public Location recalculateSpawn() {
        Location center = getCuboid().getCenter();

        int maxY = getCuboid().getUpperY();
        int maxX = getCuboid().getUpperX();
        int maxZ = getCuboid().getUpperZ();
        int minX = getCuboid().getLowerX();
        int minY = getCuboid().getLowerY();
        int minZ = getCuboid().getLowerZ();

        Optional<Location> safe = LocationUtil.findSafeLocationSpirally(center, maxX, maxY, maxZ, minX, minY, minZ);
        return safe.orElse(center);
    }

    @Override
    public void setCuboid(Cuboid cuboid) {
        Validation.isTrue(cuboid.contains(spawn), "skyblockIsland.setCuboid.not_contains_spawn");
        this.cuboid = cuboid;
    }

    public void setExtendLevel(int extendLevel) {
        Validation.isTrue(extendLevel >= 0, "skyblockIsland.setExtendLevel.less_than_zero");
        this.extendLevel = extendLevel;
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    @Override
    public void join(IslandMember member) {
        Validation.isTrue(isIslander(member), "island.validate.member_not_islander");
        super.join(member);
        AbstractIslandMember abstractIslandMember = (AbstractIslandMember) member;
        abstractIslandMember.addIsland(this);
    }

    @Override
    public void exit(IslandMember member) {
        super.exit(member);
        AbstractIslandMember abstractIslandMember = (AbstractIslandMember) member;
        abstractIslandMember.removeIsland(this);
    }

    private boolean isIslander(IslandMember islandMember) {
        return islandMember instanceof Islander;
    }
}
