package pl.subtelny.islands.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.DatabaseConfiguration;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;
import pl.subtelny.islands.service.IslanderService;
import pl.subtelny.utilities.PlayerUtil;
import pl.subtelny.utilities.location.LocationUtil;

@Component
public class PlayerEventListener implements Listener {

    private final DatabaseConfiguration databaseConfiguration;

    private final IslanderService islanderService;

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public PlayerEventListener(DatabaseConfiguration databaseConfiguration,
                               IslanderService islanderService,
                               IslandActionGuard islandActionGuard) {
        this.databaseConfiguration = databaseConfiguration;
        this.islanderService = islanderService;
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        islanderService.loadIslander(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Location to = e.getTo();
        if (to == null) {
            return;
        }
        Location from = e.getFrom();
        if (LocationUtil.isSameLocationPrecisionToBlock(from, to)) {
            return;
        }

        Player player = e.getPlayer();
        IslandActionGuardResult result = islandActionGuard.accessToEnter(player, to);
        if (isAccessToActionRejected(result)) {
            e.setCancelled(true);
            Location teleportTo = LocationUtil.toLocationWithCenteredBlock(from);
            player.teleport(teleportTo);

            if (result.isIslandLoading()) {
                PlayerUtil.message(player, "Wyspa jest w trakcie ladowania");
            }
        }
    }

    @EventHandler
    public void onPlayerLeashEntity(PlayerLeashEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getEntity();

        IslandActionGuardResult result = islandActionGuard.accessToInteract(player, entity);
        if (isAccessToActionRejected(result)) {
            e.setCancelled(true);
        }
    }

    private boolean isAccessToActionRejected(IslandActionGuardResult result) {
        return IslandActionGuardResult.ACTION_PERMITED != result;
    }

}
