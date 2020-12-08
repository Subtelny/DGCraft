package pl.subtelny.gui.api.crate.session;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.subtelny.gui.api.crate.model.CrateId;

import java.util.Optional;

public interface PlayerCrateSessionService {

    Optional<PlayerCrateSession> getSession(Player player);

    boolean hasSession(Player player);

    void closeSession(Player player);

    void closeInventory(Player player);

    PlayerCrateSession openSession(Player player, CrateId crateId);

    void closeAllSessions(Plugin plugin);

    void closeAllSessions(CrateId crateId);

}
