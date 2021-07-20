package pl.subtelny.islands.islander.model;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.islands.configuration.IslandsConfiguration;
import pl.subtelny.islands.api.*;
import pl.subtelny.islands.api.membership.model.AbstractIslandMember;

import java.util.List;
import java.util.Objects;

public class Islander extends AbstractIslandMember {

    public final static IslandMemberType TYPE = new IslandMemberType("ISLANDER");

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

    @Override
    public IslandMemberId getIslandMemberId() {
        return IslandMemberId.of(TYPE.getInternal(), islanderId.getInternal().toString());
    }

    @Override
    public String getName() {
        return account.getDisplayName();
    }

    public IslanderId getIslanderId() {
        return islanderId;
    }

    @Override
    public boolean isOnline() {
        return getPlayer() != null;
    }

    @Override
    public void sendMessage(String message) {
        Player player = getPlayer();
        if (player != null && player.isOnline()) {
            player.sendMessage(message);
        }
    }

    @Override
    public void sendMessage(BaseComponent component) {
        sendMessage(component.toLegacyText());
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(islanderId.getInternal());
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
