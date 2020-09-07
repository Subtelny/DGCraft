package pl.subtelny.islands.island;

import org.bukkit.Location;
import pl.subtelny.groups.api.Group;
import pl.subtelny.groups.api.GroupId;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Island {

    IslandId getId();

    Cuboid getCuboid();

    LocalDateTime getCreatedDate();

    int getPoints();

    Set<IslandMember> getMembers();

    Optional<IslandMember> getOwner();

    Location getSpawn();

    boolean isInIsland(IslandMember member);

    void join(IslandMember member);

    void exit(IslandMember member);

    void changeSpawn(Location spawn);

    void setCuboid(Cuboid cuboid);

    void setPoints(int points);

}
