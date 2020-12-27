package pl.subtelny.islands.island.repository.anemia;

import org.bukkit.Location;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandType;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class IslandAnemia {

    private IslandId islandId;

    private IslandType islandType;

    private LocalDateTime createdDate;

    private Location spawn;

    private int points;

    public IslandAnemia() {
    }

    public IslandAnemia(IslandId islandId, LocalDateTime createdDate, Location spawn, int points) {
        this.islandId = islandId;
        this.createdDate = createdDate;
        this.spawn = spawn;
        this.points = points;
    }

    public IslandId getIslandId() {
        return islandId;
    }

    public void setIslandId(IslandId islandId) {
        this.islandId = islandId;
    }

    public IslandType getIslandType() {
        return islandType;
    }

    public void setIslandType(IslandType islandType) {
        this.islandType = islandType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandAnemia that = (IslandAnemia) o;
        return points == that.points &&
                Objects.equals(islandId, that.islandId) &&
                Objects.equals(islandType, that.islandType) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(spawn, that.spawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandId, islandType, createdDate, spawn, points);
    }

}
