package pl.subtelny.crate.model;

import org.bukkit.entity.Player;

public interface Crate {

    CrateId getId();

    boolean click(Player player, int slot);

}
