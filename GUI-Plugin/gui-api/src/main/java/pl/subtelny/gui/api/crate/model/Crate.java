package pl.subtelny.gui.api.crate.model;

import org.bukkit.entity.Player;

public interface Crate {

    void click(Player player, int slot);

    ItemCrate getItemCrate(int slot);

    void setItemCrate(int slot, ItemCrate itemCrate);

}
