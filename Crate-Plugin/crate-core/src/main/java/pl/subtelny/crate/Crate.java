package pl.subtelny.crate;

import org.bukkit.entity.Player;

public interface Crate {

    CrateKey getKey();

    CrateClickResult click(Player player, int slot);

    void open(Player player);

}
