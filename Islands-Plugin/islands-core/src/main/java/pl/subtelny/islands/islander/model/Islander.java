package pl.subtelny.islands.islander.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.membership.model.AbstractIslandMember;

import java.util.List;
import java.util.Objects;

public class Islander extends AbstractIslandMember {

    public final static IslandMemberType ISLAND_MEMBER_TYPE = new IslandMemberType("ISLANDER");

    private final IslanderId islanderId;

    private final Account account;

    public Islander(IslanderId islanderId,
                    List<IslandId> islandIds,
                    Account account) {
        super(islandIds);
        this.islanderId = islanderId;
        this.account = account;
    }

    @Override
    public void leaveIsland(Island island) {
        super.leaveIsland(island);
        teleportToSpawn(island);
    }

    private void teleportToSpawn(Island island) {
        Player player = getPlayer();
        if (player.isOnline()) {
            if (island.getCuboid().contains(player.getLocation())) {
                player.teleport(IslandsConfiguration.MAIN_WORLD.getSpawnLocation());
            }
        }
    }

    @Override
    public IslandMemberId getIslandMemberId() {
        return IslandMemberId.of(ISLAND_MEMBER_TYPE.getInternal(), islanderId.getInternal().toString());
    }

    @Override
    public String getName() {
        return account.getDisplayName();
    }

    public IslanderId getIslanderId() {
        return islanderId;
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
