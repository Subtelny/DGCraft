package pl.subtelny.islands.island.model;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import pl.subtelny.islands.island.*;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class AbstractIsland implements Island {

    private final Cache<IslandMember, Long> pendingJoinRequests = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final List<IslandMemberChangedRequest> islandMembersChangesRequests = new ArrayList<>();

    private final IslandId islandId;

    private final IslandType islandType;

    private final LocalDateTime creationDate;

    private final List<IslandMemberId> islandMemberIds;

    private final Configuration configuration = new Configuration();

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
                          IslandMemberId owner) {
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
    public Map<IslandMember, Long> getPendingJoinRequests() {
        return new HashMap<>(pendingJoinRequests.asMap());
    }

    @Override
    public Optional<IslandMemberId> getOwner() {
        return Optional.ofNullable(owner);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean isMemberOfIsland(IslandMember member) {
        return islandMemberIds.contains(member.getIslandMemberId());
    }

    @Override
    public boolean isOwner(IslandMember islandMember) {
        return islandMember.getIslandMemberId().equals(owner);
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
        if (owner != null) {
            islandMembersChangesRequests.add(IslandMemberChangedRequest.update(owner, false));
        }
        islandMembersChangesRequests.add(IslandMemberChangedRequest.update(newOwner, true));
        this.owner = newOwner;
    }

    @Override
    public void join(IslandMember member) {
        Validation.isFalse(islandMemberIds.contains(member.getIslandMemberId()), "island.member_already_added");
        islandMemberIds.add(member.getIslandMemberId());
        islandMembersChangesRequests.add(IslandMemberChangedRequest.added(member));
        member.addIsland(this);
    }

    @Override
    public void leave(IslandMember member) {
        Validation.isTrue(islandMemberIds.contains(member.getIslandMemberId()), "island.member_not_added");
        islandMemberIds.remove(member.getIslandMemberId());
        islandMembersChangesRequests.add(IslandMemberChangedRequest.removed(member));
        member.leaveIsland(this);
    }

    @Override
    public void askJoin(IslandMember islandMember) {
        Validation.isTrue(pendingJoinRequests.getIfPresent(islandMember) == null, "island.ask_join.already_added");
        Validation.isFalse(islandMemberIds.contains(islandMember.getIslandMemberId()), "island.ask_join.already_added");
        pendingJoinRequests.put(islandMember, System.currentTimeMillis());
    }

    @Override
    public boolean canAskJoin(IslandMember islandMember) {
        return pendingJoinRequests.getIfPresent(islandMember) == null
                && !islandMemberIds.contains(islandMember.getIslandMemberId());
    }

    @Override
    public void acceptAskJoin(IslandMember islandMember) {
        Validation.isFalse(pendingJoinRequests.getIfPresent(islandMember) == null, "island.ask_join.request_not_found");
        pendingJoinRequests.invalidate(islandMember);
        join(islandMember);
    }

    public List<IslandMemberChangedRequest> getIslandMembersChangesRequests() {
        return islandMembersChangesRequests;
    }

}
