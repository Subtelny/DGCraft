package pl.subtelny.islands.island.confirmation;

import org.bukkit.entity.Player;
import pl.subtelny.core.api.confirmation.Confirmable;
import pl.subtelny.islands.island.Island;

import java.util.Objects;

public class IslandConfirmable implements Confirmable {

    private final Island island;

    public IslandConfirmable(Island island) {
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
        IslandConfirmable that = (IslandConfirmable) o;
        return Objects.equals(island, that.island);
    }

    @Override
    public int hashCode() {
        return Objects.hash(island);
    }
}
