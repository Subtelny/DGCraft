package pl.subtelny.islands.island.model;

import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.utilities.Validation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractIslandMember implements IslandMember {

    private final List<Island> islands = new ArrayList<>();

    public void addIsland(Island island) {
        Validation.isTrue(island.isInIsland(this), "IslandMember %s is not added into island %s", this.getId().getInternal(), island.getId().getInternal());
        islands.add(island);
    }

    public void removeIsland(Island island) {
        Validation.isTrue(island.isInIsland(this), "First remove IslandMember %s from island %s", this.getId().getInternal(), island.getId().getInternal());
        islands.remove(island);
    }

    @Override
    public List<Island> getIslands() {
        return new ArrayList<>(islands);
    }
}
