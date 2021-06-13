package pl.subtelny.crate.api.listener;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.Crate;

public interface CrateListener {

    CrateListener EMPTY = (player, crate) -> {
        //Noop
    };

    void handle(Player player, Crate crate);

}
