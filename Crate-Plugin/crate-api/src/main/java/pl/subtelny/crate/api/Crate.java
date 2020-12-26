package pl.subtelny.crate.api;

import org.bukkit.entity.Player;

public interface Crate {

    CrateId getId();

    CrateClickResult click(Player player, int slot);

    void open(Player player);

    void cleanIfNeeded();

    void closeAllSessions();
}
