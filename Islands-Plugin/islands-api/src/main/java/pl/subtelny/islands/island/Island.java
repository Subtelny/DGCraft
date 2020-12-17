package pl.subtelny.islands.island;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.configuration.Configuration;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Island {

    IslandId getId();

    LocalDateTime getCreationDate();

    List<IslandMember> getMembers();

    Optional<IslandMember> getOwner();

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

    void exit(IslandMember member);

    void changeSpawn(Location spawn);

    void updateCuboid(Cuboid cuboid);

    void setPoints(int points);

    void askJoin(IslandMember islandMember);

}
