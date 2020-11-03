package pl.subtelny.islands.islander.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.membership.AbstractIslandMember;
import pl.subtelny.islands.island.query.IslandQueryService;

import java.util.List;
import java.util.Objects;

public class Islander extends AbstractIslandMember {

    public final static String ISLAND_MEMBER_TYPE = "ISLANDER";

    private final IslanderId islanderId;

    public Islander(IslanderId islanderId, List<IslandId> islandIds, IslandQueryService islandQueryService) {
        super(islandIds, islandQueryService);
        this.islanderId = islanderId;
    }

    public IslanderId getIslanderId() {
        return islanderId;
    }

    @Override
    public IslandMemberId getId() {
        return IslandMemberId.of(ISLAND_MEMBER_TYPE, islanderId.getInternal().toString());
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
