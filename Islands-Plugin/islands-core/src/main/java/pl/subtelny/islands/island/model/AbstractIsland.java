package pl.subtelny.islands.island.model;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.islandmember.IslandMemberQueryService;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractIsland implements Island {

    private final List<IslandMemberChangedRequest> islandMembersChangesRequests = new ArrayList<>();

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandId islandId;

    private final IslandType islandType;

    private final LocalDateTime creationDate;

    private final List<IslandMemberId> islandMemberIds;

    protected Cuboid cuboid;

    private IslandMemberId owner;

    private Location spawn;

    private int points;

    public AbstractIsland(IslandMemberQueryService islandMemberQueryService,
                          IslandId islandId,
                          IslandType islandType,
                          LocalDateTime creationDate,
                          Cuboid cuboid,
                          Location spawn,
                          int points,
                          List<IslandMemberId> islandMemberIds,
                          IslandMemberId owner) {
        this.islandType = islandType;
        if (owner != null) {
            Validate.isTrue(islandMemberIds.contains(owner), "Not found owner in list of members");
        }
        this.islandMemberQueryService = islandMemberQueryService;
        this.islandId = islandId;
        this.creationDate = creationDate;
        this.cuboid = cuboid;
        this.spawn = spawn;
        this.points = points;
        this.islandMemberIds = islandMemberIds;
        this.owner = owner;
    }

    @Override
    public IslandId getId() {
        return islandId;
    }

    @Override
    public IslandType getIslandType() {
        return islandType;
    }

    @Override
    public Location getSpawn() {
        return spawn;
    }

    @Override
    public void changeSpawn(Location spawn) {
        Validation.isTrue(cuboid.contains(spawn), "island.new_spawn_not_in_cuboid");
        Validation.isTrue(LocationUtil.isSafeForPlayer(spawn), "island.new_spawn_not_safe");
        this.spawn = spawn;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public void updateCuboid(Cuboid cuboid) {
        Validation.isTrue(this.cuboid.getWorld().equals(cuboid.getWorld()), "skyblockIsland.update_cuboid_diff_worlds");
        this.cuboid = cuboid;
    }

    @Override
    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    public List<IslandMember> getMembers() {
        return Collections.unmodifiableList(islandMemberQueryService.getIslandMembers(islandMemberIds));
    }

    @Override
    public Optional<IslandMember> getOwner() {
        return islandMemberQueryService.findIslandMember(owner);
    }

    @Override
    public void changeOwner(IslandMember islandMember) {
        IslandMemberId newOwner = islandMember.getId();
        Validation.isTrue(islandMemberIds.contains(newOwner), "island.member_not_added_to_owner");
        if (owner != null) {
            islandMembersChangesRequests.add(IslandMemberChangedRequest.update(owner, false));
        }
        islandMembersChangesRequests.add(IslandMemberChangedRequest.update(newOwner, true));
        this.owner = newOwner;
    }

    @Override
    public boolean isMemberOfIsland(IslandMember member) {
        return islandMemberIds.contains(member.getId());
    }

    @Override
    public void join(IslandMember member) {
        Validation.isFalse(islandMemberIds.contains(member.getId()), "island.member_already_added");
        islandMemberIds.add(member.getId());
        islandMembersChangesRequests.add(IslandMemberChangedRequest.added(member));
    }

    @Override
    public void exit(IslandMember member) {
        Validation.isTrue(islandMemberIds.contains(member.getId()), "island.member_not_added");
        islandMemberIds.remove(member.getId());
        islandMembersChangesRequests.add(IslandMemberChangedRequest.removed(member));
    }

    public List<IslandMemberChangedRequest> getIslandMembersChangesRequests() {
        return islandMembersChangesRequests;
    }

}
