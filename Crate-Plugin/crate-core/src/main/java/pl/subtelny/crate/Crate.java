package pl.subtelny.crate;

import org.bukkit.entity.Player;
import pl.subtelny.crate.click.ActionType;
import pl.subtelny.crate.click.CrateClickResult;

public interface Crate {

    void open(Player player);

    CrateClickResult click(Player player, ActionType actionType, int slot);

    CrateId getCrateId();

    CrateData getCrateData();

    boolean isShared();

}
