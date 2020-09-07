package pl.subtelny.islands.islander.model;

import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.model.AbstractIslandMember;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Objects;
import java.util.Optional;

public class Islander extends AbstractIslandMember {

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
            throw ValidationException.of("islander.setSkyblockIsland.not_member_new_island");
        } else if (this.skyblockIsland.isInIsland(this)) {
            throw ValidationException.of("islander.setSkyblockIsland.already_member_new_island");
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

    @Override
    public IslandMemberId getId() {
        return IslandMemberId.of(islanderId);
    }
}
