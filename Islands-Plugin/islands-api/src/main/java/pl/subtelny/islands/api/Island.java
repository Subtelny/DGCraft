package pl.subtelny.islands.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Island {

    IslandId getId();

    LocalDateTime getCreationDate();

    List<IslandMemberId> getMembers();

    Optional<IslandMemberId> getOwner();

    default boolean isOwner(IslandMember islandMember) {
        return isOwner(islandMember.getIslandMemberId());
    }

    boolean isOwner(IslandMemberId islandMemberId);

    Location getSpawn();

    Cuboid getCuboid();

    IslandType getIslandType();

    World getWorld();

    IslandConfiguration getConfiguration();

    String getName();

    int getPoints();

    void setPoints(int points);

    default boolean isMember(IslandMember member) {
        return isMember(member.getIslandMemberId());
    }

    boolean isMember(IslandMemberId islandMemberId);

    void changeOwner(IslandMember islandMember);

    void join(IslandMember member);

    void leave(IslandMember member);

    void changeSpawn(Location spawn);

    void updateCuboid(Cuboid cuboid);

    void changeBiome(Biome biome);

    void addAskRequest(IslandMember islandMember, ConfirmContextId confirmContextId);

    Map<IslandMember, ConfirmContextId> getAskRequests();
}
