package pl.subtelny.islands.islander.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.membership.model.AbstractIslandMember;
import pl.subtelny.islands.island.query.IslandQueryService;

import java.util.List;
import java.util.Objects;

public class Islander extends AbstractIslandMember {

    public final static String ISLAND_MEMBER_TYPE = "ISLANDER";

    private final IslanderId islanderId;

    private final Account account;

    public Islander(IslanderId islanderId,
                    List<IslandId> islandIds,
                    Account account) {
        super(islandIds);
        this.islanderId = islanderId;
        this.account = account;
    }

    public IslanderId getIslanderId() {
        return islanderId;
    }

    @Override
    public IslandMemberId getIslandMemberId() {
        return IslandMemberId.of(ISLAND_MEMBER_TYPE, islanderId.getInternal().toString());
    }

    @Override
    public String getName() {
        return account.getDisplayName();
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(islanderId.getInternal());
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
