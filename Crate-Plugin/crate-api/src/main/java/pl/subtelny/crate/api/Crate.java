package pl.subtelny.crate.api;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.CrateId;

public interface Crate {

    CrateId getId();

    boolean click(Player player, int slot);

    void open(Player player);

    void cleanIfNeeded();

    void closeAllSessions();
}
