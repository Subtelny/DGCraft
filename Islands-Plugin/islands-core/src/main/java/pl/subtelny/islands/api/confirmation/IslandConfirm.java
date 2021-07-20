package pl.subtelny.islands.api.confirmation;

import org.bukkit.entity.Player;
import pl.subtelny.core.api.confirmation.Confirmable;
import pl.subtelny.islands.api.Island;

import java.util.Objects;

public class IslandConfirm implements Confirmable {

    private final Island island;

    public IslandConfirm(Island island) {
        this.island = island;
    }

    @Override
    public boolean canConfirm(Player player) {
        return island.getMembers()
                .stream()
                .filter(islandMemberId -> islandMemberId.getValue().equals(player.getUniqueId().toString()))
                .map(island::isOwner)
                .findFirst()
                .orElse(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandConfirm that = (IslandConfirm) o;
        return Objects.equals(island, that.island);
    }

    @Override
    public int hashCode() {
        return Objects.hash(island);
    }
}
