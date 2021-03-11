package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;
import pl.subtelny.islands.islander.IslanderCommandService;
import pl.subtelny.utilities.location.LocationUtil;

@Component
public class PlayerEventListener implements Listener {

    private final IslanderCommandService islanderCommandService;

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public PlayerEventListener(IslanderCommandService islanderCommandService, IslandActionGuard islandActionGuard) {
        this.islanderCommandService = islanderCommandService;
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        islanderCommandService.loadIslander(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock != null) {
            IslandActionGuardResult result = islandActionGuard.accessToBuild(player, clickedBlock.getLocation());
            if (result.isActionProhibited()) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        IslandActionGuardResult result = islandActionGuard.accessToInteract(player, e.getRightClicked());
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if (LocationUtil.isSameLocationPrecisionToBlock(from, to)) {
            return;
        }

        Player player = e.getPlayer();
        IslandActionGuardResult result = islandActionGuard.accessToEnter(player, to);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
            Location teleportTo = LocationUtil.toLocationWithCenteredBlock(from);
            player.teleport(teleportTo);
        }
    }

    @EventHandler
    public void onPlayerLeashEntity(PlayerLeashEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getEntity();

        IslandActionGuardResult result = islandActionGuard.accessToInteract(player, entity);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

}
