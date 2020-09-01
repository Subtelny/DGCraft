package pl.subtelny.islands.island.model;

import org.bukkit.Location;
import pl.subtelny.groups.api.Group;
import pl.subtelny.groups.api.GroupId;
import pl.subtelny.groups.api.GroupsContext;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.*;

public abstract class AbstractIsland implements Island {

    private final IslandId islandId;

    private final LocalDateTime createdDate;

    protected Cuboid cuboid;

    protected Location spawn;

    protected GroupsContext groupsContext;

    private int points;

    public AbstractIsland(IslandId islandId, Location spawn, LocalDateTime createdDate, Cuboid cuboid) {
        this.islandId = islandId;
        this.spawn = spawn;
        this.createdDate = createdDate;
        this.cuboid = cuboid;
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
        return null;
    }

    @Override
    public Location getSpawn() {
        return spawn;
    }

    @Override
    public boolean isInIsland(IslandMember member) {
        return false;//members.containsKey(member);
    }

    @Override
    public void join(IslandMember member, GroupId rank) {

    }

    @Override
    public void exit(IslandMember member) {

    }

    @Override
    public void addRank(IslandMember member, GroupId groupId) {

    }

    @Override
    public void removeRank(IslandMember member, GroupId groupId) {

    }

    /*@Override
    public void join(IslandMember member, IslandMemberRank rank) {
        Validation.isTrue(!members.containsKey(member), "island.join_already_member", member.getId());
        validateUniqueRank(member, rank);
        members.put(member, rank);
    }

    @Override
    public void exit(IslandMember member) {
        validateIsMember(member);
        members.remove(member);
    }

    @Override
    public void changeRank(IslandMember member, IslandMemberRank rank) {
        validateIsMember(member);
        validateUniqueRank(member, rank);
        members.put(member, rank);
    }

    private void validateIsMember(IslandMember member) {
        Validation.isTrue(members.containsKey(member), "island.changeRank.not_member", member.getId());
    }

    private void validateUniqueRank(IslandMember member, IslandMemberRank rank) {
        if (rank.isUnique()) {
            Optional<IslandMember> islandMemberOpt = getMembers().entrySet().stream()
                    .filter(entry -> entry.getValue().equals(rank))
                    .map(Map.Entry::getKey)
                    .filter(islandMember -> !islandMember.equals(member))
                    .findAny();
            Validation.isTrue(islandMemberOpt.isEmpty(), "island.changeRank.rank_unique", member.getId(), rank);
        }
    }*/

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
