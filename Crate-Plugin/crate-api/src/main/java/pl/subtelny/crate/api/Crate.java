package pl.subtelny.crate.api;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.click.ActionType;
import pl.subtelny.crate.api.click.CrateClickResult;
import pl.subtelny.crate.api.item.ItemCrate;

public interface Crate {

    void open(Player player);

    default void close(Player player) {
        //Noop
    }

    CrateClickResult click(Player player, ActionType actionType, int slot);

    CrateId getCrateId();

    CrateData getCrateData();

    boolean isShared();

    boolean addItemCrate(ItemCrate itemCrate);

    void setItemCrate(int slot, ItemCrate itemCrate);

}
