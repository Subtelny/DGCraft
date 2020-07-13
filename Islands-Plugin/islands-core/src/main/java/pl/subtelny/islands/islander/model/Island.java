package pl.subtelny.islands.islander.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import pl.subtelny.islands.skyblockisland.model.MembershipType;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.location.LocationUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class Island {

    private final IslandId islandId;

    protected Map<Islander, MembershipType> members = new HashMap<>();

    protected LocalDateTime createdDate;

    protected Location spawn;

    protected Cuboid cuboid;

    public Island(IslandId islandId, Cuboid cuboid) {
        this.islandId = islandId;
        this.cuboid = cuboid;
    }

    public void changeSpawn(Location spawn) {
        if (!LocationUtil.isSafeForPlayer(spawn)) {
            throw ValidationException.of("island.changeSpawn.not_solid_block");
        }
        this.spawn = spawn;
    }

    public boolean isInIsland(Islander islander) {
        return members.containsKey(islander);
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Map<Islander, MembershipType> getMembers() {
        return new HashMap<>(members);
    }

    public abstract void addMember(Islander islander);

    public abstract void removeMember(Islander islander);

    public abstract void recalculateSpawn();

    public abstract IslandType getIslandType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Island island = (Island) o;

        return new EqualsBuilder()
                .append(islandId, island.islandId)
                .append(createdDate, island.createdDate)
                .append(spawn, island.spawn)
                .append(cuboid, island.cuboid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(islandId)
                .append(createdDate)
                .append(spawn)
                .append(cuboid)
                .toHashCode();
    }
}
