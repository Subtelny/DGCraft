package pl.subtelny.islands.island.model;

import org.bukkit.Location;
import pl.subtelny.groups.api.Group;
import pl.subtelny.groups.api.GroupMemberId;
import pl.subtelny.groups.api.GroupsContext;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractIsland implements Island {

    private final IslandId islandId;

    private final LocalDateTime createdDate;

    private final GroupsContext groupsContext;

    protected final List<IslandMember> islandMembers = new ArrayList<>();

    protected Cuboid cuboid;

    protected Location spawn;

    private int points;

    public AbstractIsland(IslandId islandId, LocalDateTime createdDate, GroupsContext groupsContext, Cuboid cuboid, Location spawn, int points) {
        this.islandId = islandId;
        this.createdDate = createdDate;
        this.groupsContext = groupsContext;
        this.cuboid = cuboid;
        this.spawn = spawn;
        this.points = points;
    }

    public AbstractIsland(IslandId islandId, LocalDateTime createdDate, GroupsContext groupsContext, Cuboid cuboid, Location spawn) {
        this.islandId = islandId;
        this.createdDate = createdDate;
        this.groupsContext = groupsContext;
        this.cuboid = cuboid;
        this.spawn = spawn;
    }

    @Override
    public IslandId getId() {
        return islandId;
    }

    @Override
    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public Map<IslandMember, List<Group>> getMembers() {
        return islandMembers.stream().collect(Collectors.toMap(
                member -> member,
                member -> groupsContext.getGroups(getGroupMemberId(member))));
    }

    @Override
    public Location getSpawn() {
        return spawn;
    }

    @Override
    public boolean isInIsland(IslandMember member) {
        return islandMembers.contains(member);
    }

    @Override
    public void join(IslandMember member) {
        Validation.isFalse(isInIsland(member), "island.validate.member_already_added");
        islandMembers.add(member);
    }

    @Override
    public void exit(IslandMember member) {
        Validation.isTrue(isInIsland(member), "island.validate.member_not_added");
        islandMembers.remove(member);
    }

    @Override
    public void changeSpawn(Location spawn) {
        Validation.isTrue(LocationUtil.isSafeForPlayer(spawn), "island.changeSpawn.not_safe");
        this.spawn = spawn;
    }

    @Override
    public void setCuboid(Cuboid cuboid) {
        Validation.isTrue(cuboid != null, "island.changeCuboid.is_null");
        this.cuboid = cuboid;
    }

    @Override
    public void setPoints(int points) {
        Validation.isTrue(points >= 0, "island.setPoints.less_than_zero");
        this.points = points;
    }

    private GroupMemberId getGroupMemberId(IslandMember islandMember) {
        return GroupMemberId.of(islandMember.getId().getInternal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractIsland that = (AbstractIsland) o;
        return islandId.equals(that.islandId) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(cuboid, that.cuboid) &&
                Objects.equals(spawn, that.spawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId);
    }
}
