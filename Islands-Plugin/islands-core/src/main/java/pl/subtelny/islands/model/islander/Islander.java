package pl.subtelny.islands.model.islander;

import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Objects;
import java.util.Optional;

public class Islander {

    private final IslanderId islanderId;

    private SkyblockIsland skyblockIsland;

    public Islander(IslanderId islanderId) {
        this.islanderId = islanderId;
    }

    public IslanderId getIslanderId() {
        return islanderId;
    }

    public Optional<SkyblockIsland> getSkyblockIsland() {
        return Optional.ofNullable(skyblockIsland);
    }

    public void setSkyblockIsland(SkyblockIsland skyblockIsland) {
        if (skyblockIsland != null && !skyblockIsland.isInIsland(this)) {
            throw ValidationException.of("islander.change_skyblock_island_not_in_new_island");
        } else if (this.skyblockIsland.isInIsland(this)) {
            throw ValidationException.of("islander.change_skyblock_island_is_in_old_island");
        }
        this.skyblockIsland = skyblockIsland;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Islander islander = (Islander) o;
        return Objects.equals(islanderId, islander.islanderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islanderId);
    }
}
