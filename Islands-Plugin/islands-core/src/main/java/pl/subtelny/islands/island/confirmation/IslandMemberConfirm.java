package pl.subtelny.islands.island.confirmation;

import org.bukkit.entity.Player;
import pl.subtelny.core.api.confirmation.Confirmable;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.islander.model.Islander;

import java.util.Objects;

public class IslandMemberConfirm implements Confirmable {

    private final IslandMember islandMember;

    public IslandMemberConfirm(IslandMember islandMember) {
        this.islandMember = islandMember;
    }

    @Override
    public boolean canConfirm(Player player) {
        //TODO
        //do refaktoru gdy pojawi sie inna implementacja do IslandMembera niz tylko Islander

        IslandMemberId islandMemberId = islandMember.getIslandMemberId();
        if (Islander.TYPE.equals(islandMemberId.getType())) {
            return islandMemberId.getValue().equals(player.getUniqueId().toString());
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandMemberConfirm that = (IslandMemberConfirm) o;
        return Objects.equals(islandMember, that.islandMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandMember);
    }
}
