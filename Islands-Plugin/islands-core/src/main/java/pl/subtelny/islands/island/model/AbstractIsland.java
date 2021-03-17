package pl.subtelny.islands.island.model;

import com.google.common.collect.MapMaker;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.islands.event.IslandEventBus;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.events.IslandMemberJoinedIslandEvent;
import pl.subtelny.islands.island.events.IslandMemberLeftIslandEvent;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.*;

public abstract class AbstractIsland implements Island {

    private final Map<IslandMember, ConfirmContextId> pendingJoinRequests = new MapMaker().weakValues().makeMap();

    private final IslandId islandId;

    private final IslandType islandType;

    private final LocalDateTime creationDate;

    private final List<IslandMemberId> islandMemberIds;

    private final IslandConfiguration configuration;

    protected Cuboid cuboid;

    private IslandMemberId owner;

    private Location spawn;

    private int points;

    public AbstractIsland(IslandId islandId,
                          IslandType islandType,
                          LocalDateTime creationDate,
                          Cuboid cuboid,
                          Location spawn,
                          int points,
                          List<IslandMemberId> islandMemberIds,
                          IslandMemberId owner,
                          IslandConfiguration configuration) {
        if (owner != null) {
            Validate.isTrue(islandMemberIds.contains(owner), "Not found owner in list of members");
        }
        this.islandType = islandType;
        this.islandId = islandId;
        this.creationDate = creationDate;
        this.cuboid = cuboid;
        this.spawn = spawn;
        this.points = points;
        this.islandMemberIds = islandMemberIds;
        this.owner = owner;
        this.configuration = configuration;
    }

    @Override
    public IslandId getId() {
        return islandId;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
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
    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    public List<IslandMemberId> getMembers() {
        return new ArrayList<>(islandMemberIds);
    }

    @Override
    public Optional<IslandMemberId> getOwner() {
        return Optional.ofNullable(owner);
    }

    @Override
    public IslandConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean isMember(IslandMemberId islandMemberId) {
        return islandMemberIds.contains(islandMemberId);
    }

    @Override
    public boolean isOwner(IslandMemberId islandMemberId) {
        return islandMemberId.equals(owner);
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void changeSpawn(Location spawn) {
        Validation.isTrue(cuboid.contains(spawn), "island.new_spawn_not_in_cuboid");
        Validation.isTrue(LocationUtil.isSafeForPlayer(spawn), "island.new_spawn_not_safe");
        this.spawn = spawn;
    }

    @Override
    public void updateCuboid(Cuboid cuboid) {
        Validation.isTrue(this.cuboid.getWorld().equals(cuboid.getWorld()), "skyblockIsland.update_cuboid_diff_worlds");
        this.cuboid = cuboid;
    }

    @Override
    public void changeOwner(IslandMember islandMember) {
        IslandMemberId newOwner = islandMember.getIslandMemberId();
        Validation.isTrue(islandMemberIds.contains(newOwner), "island.member_not_added_to_owner");
        this.owner = newOwner;
    }

    @Override
    public void join(IslandMember member) {
        Validation.isFalse(islandMemberIds.contains(member.getIslandMemberId()), "island.member_already_added");
        islandMemberIds.add(member.getIslandMemberId());
        member.addIsland(this);
        IslandEventBus.call(new IslandMemberJoinedIslandEvent(member, this));
    }

    @Override
    public void leave(IslandMember member) {
        Validation.isTrue(islandMemberIds.contains(member.getIslandMemberId()), "island.member_not_added");
        islandMemberIds.remove(member.getIslandMemberId());
        member.leaveIsland(this);
        IslandEventBus.call(new IslandMemberLeftIslandEvent(member, this));
    }

    @Override
    public void addAskRequest(IslandMember islandMember, ConfirmContextId confirmContextId) {
        Validation.isFalse(islandMemberIds.contains(islandMember.getIslandMemberId()), "IslandMember already added");
        pendingJoinRequests.put(islandMember, confirmContextId);
    }

    @Override
    public Map<IslandMember, ConfirmContextId> getAskRequests() {
        return new HashMap<>(pendingJoinRequests);
    }

}
