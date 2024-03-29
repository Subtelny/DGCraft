package pl.subtelny.islands.islander.repository.anemia;

import pl.subtelny.islands.api.IslanderId;

import java.util.Objects;

public class IslanderAnemia {

    private IslanderId islanderId;

    public IslanderAnemia(IslanderId islanderId) {
        this.islanderId = islanderId;
    }

    public IslanderAnemia() { }

    public IslanderId getIslanderId() {
        return islanderId;
    }

    public void setIslanderId(IslanderId islanderId) {
        this.islanderId = islanderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslanderAnemia that = (IslanderAnemia) o;
        return Objects.equals(islanderId, that.islanderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islanderId);
    }
}
