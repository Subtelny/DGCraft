package pl.subtelny.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ServerInitializeListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent e) {
        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Serwer dopiero sie uruchamia");
    }

}
