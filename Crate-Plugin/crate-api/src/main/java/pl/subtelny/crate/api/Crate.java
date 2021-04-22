package pl.subtelny.crate.api;

import org.bukkit.entity.Player;

public interface Crate {

    CrateKey getKey();

    CrateClickResult click(Player player, int slot);

    CrateData getData();

    void open(Player player);

    default void close() {
        //Noop
    }

}
