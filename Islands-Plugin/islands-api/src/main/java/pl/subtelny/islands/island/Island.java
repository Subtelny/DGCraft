package pl.subtelny.islands.island;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.utilities.configuration.Configuration;
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

    Configuration getConfiguration();

    String getName();

    int getPoints();

    boolean isMemberOfIsland(IslandMember member);

    void changeOwner(IslandMember islandMember);

    void join(IslandMember member);

    void leave(IslandMember member);

    void changeSpawn(Location spawn);

    void updateCuboid(Cuboid cuboid);

    void setPoints(int points);

}
