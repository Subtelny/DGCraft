package pl.subtelny.islands.island.model;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.membership.IslandMemberQueryService;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public abstract class AbstractIsland implements Island {

    private final Cache<IslandMember, Long> pendingJoinRequests = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final List<IslandMemberChangedRequest> islandMembersChangesRequests = new ArrayList<>();

    private final IslandMemberQueryService islandMemberQueryService;

    private final IslandId islandId;

    private final IslandType islandType;

    private final LocalDateTime creationDate;

    private final List<IslandMemberId> islandMemberIds;

    private final Configuration configuration = new Configuration();

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
        if (owner != null) {
            Validate.isTrue(islandMemberIds.contains(owner), "Not found owner in list of members");
        }
        this.islandType = islandType;
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
    public List<IslandMember> getMembers() {
        return Collections.unmodifiableList(islandMemberQueryService.getIslandMembers(islandMemberIds));
    }

    @Override
    public Optional<IslandMember> getOwner() {
        return islandMemberQueryService.findIslandMember(owner);
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
    }

    @Override
    public void exit(IslandMember member) {
        Validation.isTrue(islandMemberIds.contains(member.getIslandMemberId()), "island.member_not_added");
        islandMemberIds.remove(member.getIslandMemberId());
        islandMembersChangesRequests.add(IslandMemberChangedRequest.removed(member));
    }

    @Override
    public void askJoin(IslandMember islandMember) {
        if (pendingJoinRequests.getIfPresent(islandMember) != null) {
            throw ValidationException.of("island.ask_join.already_asked", getName());
        }
        pendingJoinRequests.put(islandMember, System.currentTimeMillis());
    }

    public List<IslandMemberChangedRequest> getIslandMembersChangesRequests() {
        return islandMembersChangesRequests;
    }

}
