package pl.subtelny.islands.island;

import org.bukkit.Location;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.time.LocalDateTime;
import java.util.Map;

public interface Island {

    IslandId getId();

    Cuboid getCuboid();

    LocalDateTime getCreatedDate();

    int getPoints();

    Map<IslandMember, IslandMemberRank> getMembers();

    IslandMemberRank getMemberRank(IslandMember member);

    Location getSpawn();

    boolean isInIsland(IslandMember member);

    void join(IslandMember member, IslandMemberRank rank);

    void exit(IslandMember member);

    void changeRank(IslandMember member, IslandMemberRank rank);

    void changeSpawn(Location spawn);

    void setCuboid(Cuboid cuboid);

    void setPoints(int points);

}
