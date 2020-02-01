package pl.subtelny.islands.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.service.IslandActionGuard;
import pl.subtelny.islands.service.IslandActionGuardResult;
import pl.subtelny.islands.service.IslanderService;

@Component
public class PlayerEventListener implements Listener {

    private final IslanderService islanderService;

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public PlayerEventListener(IslanderService islanderService,
                               IslandActionGuard islandActionGuard) {
        this.islanderService = islanderService;
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        islanderService.createIslanderIfNotExists(player);
    }

    @EventHandler
    public void onPlayerLeashEntity(PlayerLeashEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getEntity();

        IslandActionGuardResult result = islandActionGuard.accessToInteract(player, entity);
        if (!hasAccessToAction(result)) {
            e.setCancelled(true);
        }
    }

    private boolean hasAccessToAction(IslandActionGuardResult result) {
        return IslandActionGuardResult.ACTION_PERMITED == result;
    }

}
