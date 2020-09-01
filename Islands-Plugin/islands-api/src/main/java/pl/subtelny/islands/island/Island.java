package pl.subtelny.islands.island;

import org.bukkit.Location;
import pl.subtelny.groups.api.Group;
import pl.subtelny.groups.api.GroupId;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface Island {

    IslandId getId();

    Cuboid getCuboid();

    LocalDateTime getCreatedDate();

    int getPoints();

    Map<IslandMember, List<Group>> getMembers();

    Location getSpawn();

    boolean isInIsland(IslandMember member);

    void join(IslandMember member, GroupId rank);

    void exit(IslandMember member);

    void addRank(IslandMember member, GroupId groupId);

    void removeRank(IslandMember member, GroupId groupId);

    void changeSpawn(Location spawn);

    void setCuboid(Cuboid cuboid);

    void setPoints(int points);

}
