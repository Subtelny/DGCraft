package pl.subtelny.islands.skyblockisland.model;

import org.bukkit.Location;
import pl.subtelny.islands.islander.model.*;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class SkyblockIsland extends Island {

    private IslandCoordinates islandCoordinates;

    private int extendLevel;

    private int points;

    public SkyblockIsland(SkyblockIslandId islandId,
                          Location spawn,
                          Cuboid cuboid,
                          LocalDateTime createdDate,
                          Map<Islander, MembershipType> members,
                          IslandCoordinates islandCoordinates,
                          int extendLevel,
                          int points) {
        super(islandId, spawn, createdDate, cuboid);
        this.spawn = cuboid.getCenter();
        this.members = members;
        this.islandCoordinates = islandCoordinates;
        this.extendLevel = extendLevel;
        this.points = points;
    }

    public SkyblockIsland(SkyblockIslandId islandId, Location spawn, Cuboid cuboid, Islander owner, IslandCoordinates islandCoordinates) {
        this(islandId, spawn, cuboid, LocalDateTime.now(), Map.of(owner, MembershipType.OWNER), islandCoordinates, 0, 0);
    }

    @Override
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

    public void changeCuboid(int extendLevel, Cuboid cuboid) {
        if (!cuboid.contains(spawn)) {
            throw ValidationException.of("skyblockIsland.extendCuboid.cuboid_not_contains_spawn");
        }
        this.cuboid = cuboid;
        this.extendLevel = extendLevel;
    }

    public void changeOwner(Islander newOwner) {
        Optional<SkyblockIsland> newOwnerIsland = newOwner.getSkyblockIsland();
        if (newOwnerIsland.isPresent()) {
            throw ValidationException.of("skyblockIsland.changeOwner.already_has_island");
        }
        members.remove(getOwner());
        members.put(newOwner, MembershipType.OWNER);
        newOwner.setSkyblockIsland(this);
    }

    public void addMember(Islander islander) {
        Optional<SkyblockIsland> islanderIsland = islander.getSkyblockIsland();
        if (islanderIsland.isPresent()) {
            throw ValidationException.of("skyblockIsland.addMember.already_has_island");
        }
        members.put(islander, MembershipType.MEMBER);
        islander.setSkyblockIsland(this);
    }

    public void removeMember(Islander islander) {
        IslanderId islanderId = islander.getIslanderId();
        if (members.containsKey(islander)) {
            members.remove(islander);
            islander.setSkyblockIsland(null);
        } else {
            throw ValidationException.of("skyblockIsland.removeMember.not_added_into_island", islanderId, getIslandId());
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw ValidationException.of("skyblockIsland.setPoints.cannot_be_less_than_zero");
        }
        this.points = points;
    }

    public Islander getOwner() {
        return members.entrySet().stream()
                .filter(entry -> entry.getValue() == MembershipType.OWNER)
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow(() -> ValidationException.of("skyblockIsland.getOwner.owner_not_found" + getIslandId()));
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    @Override
    public SkyblockIslandId getIslandId() {
        return (SkyblockIslandId) super.getIslandId();
    }

    @Override
    public IslandType getIslandType() {
        return IslandType.SKYBLOCK;
    }

}
