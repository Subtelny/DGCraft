package pl.subtelny.gui.api.crate.session;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.api.crate.model.Crate;

import java.util.Optional;

public interface PlayerCrateSessionService {

    Optional<PlayerCrateSession> getSession(Player player);

    boolean hasSession(Player player);

    void closeSession(Player player);

    void closeInventory(Player player);

    void openSession(Player player, Crate crate);

    void closeAllSessions(Plugin plugin);
}
