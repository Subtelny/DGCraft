package pl.subtelny.islands.island;

import org.bukkit.Location;
import org.bukkit.World;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Island {

    IslandId getId();

    LocalDateTime getCreationDate();

    boolean isMemberOfIsland(IslandMember member);

    List<IslandMember> getMembers();

    Optional<IslandMember> getOwner();

    void changeOwner(IslandMember islandMember);

    void join(IslandMember member);

    void exit(IslandMember member);

    void changeSpawn(Location spawn);

    Location getSpawn();

    void updateCuboid(Cuboid cuboid);

    Cuboid getCuboid();

    void setPoints(int points);

    int getPoints();

    IslandType getIslandType();

    World getWorld();

}
