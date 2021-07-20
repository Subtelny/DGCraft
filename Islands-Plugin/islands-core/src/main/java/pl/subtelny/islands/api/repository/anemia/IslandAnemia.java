package pl.subtelny.islands.api.repository.anemia;

import org.bukkit.Location;
import org.jooq.DSLContext;
import pl.subtelny.generated.tables.tables.Islands;
import pl.subtelny.generated.tables.tables.records.IslandsRecord;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.utilities.location.LocationSerializer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class IslandAnemia {

    private IslandId islandId;

    private IslandType islandType;

    private LocalDateTime createdDate;

    private Location spawn;

    private int points;

    public IslandAnemia() {
    }

    public IslandAnemia(IslandId islandId, IslandType islandType, LocalDateTime createdDate, Location spawn, int points) {
        this.islandId = islandId;
        this.islandType = islandType;
        this.createdDate = createdDate;
        this.spawn = spawn;
        this.points = points;
    }

    public IslandAnemia(Location spawn, IslandType islandType) {
        this.spawn = spawn;
        this.islandType = islandType;
        this.createdDate = LocalDateTime.now();
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

    public IslandsRecord toRecord(DSLContext context) {
        IslandsRecord islandsRecord = context.newRecord(Islands.ISLANDS);
        Timestamp createdDate = Timestamp.valueOf(getCreatedDate());
        if (getIslandId() != null) {
            islandsRecord.setId(getIslandId().getId());
        }
        islandsRecord.setType(getIslandType().getInternal());
        islandsRecord.setCreatedDate(createdDate);

        String serializedSpawn = LocationSerializer.serializeMinimalistic(getSpawn());
        islandsRecord.setSpawn(serializedSpawn);
        islandsRecord.setPoints(getPoints());
        return islandsRecord;
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
