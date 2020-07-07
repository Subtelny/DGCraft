package pl.subtelny.islands.skyblockisland.model;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.skyblockisland.repository.SkyblockIslandId;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.location.LocationUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SkyblockIsland extends Island {

    private Map<Islander, MembershipType> members = new HashMap<>();

    private IslandCoordinates islandCoordinates;

    private int extendLevel;

    private int points;

    public SkyblockIsland(SkyblockIslandId islandId, Cuboid cuboid, Map<Islander, MembershipType> members, IslandCoordinates islandCoordinates, int extendLevel, int points) {
        super(islandId, cuboid);
        this.members = members;
        this.islandCoordinates = islandCoordinates;
        this.extendLevel = extendLevel;
        this.points = points;
    }

    public SkyblockIsland(IslandId islandId, Cuboid cuboid) {
        super(islandId, cuboid);
    }

    @Override
    public boolean isInIsland(Islander islander) {
        return members.containsKey(islander);
    }

    @Override
    public void recalculateSpawn() {
        Location center = getCuboid().getCenter();

        int maxY = getCuboid().getUpperY();
        int maxX = getCuboid().getUpperX();
        int maxZ = getCuboid().getUpperZ();
        int minX = getCuboid().getLowerX();
        int minY = getCuboid().getLowerY();
        int minZ = getCuboid().getLowerZ();

        Optional<Location> safe = LocationUtil.findSafeLocationSpirally(center, maxX, maxY, maxZ, minX, minY, minZ);
        safe.ifPresent(this::changeSpawn);
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
                .orElseThrow(() -> ValidationException.of("Could not find owner for skyblock island " + getIslandId()));
    }

    public Set<Islander> getMembers() {
        return Sets.newHashSet(members.keySet());
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
