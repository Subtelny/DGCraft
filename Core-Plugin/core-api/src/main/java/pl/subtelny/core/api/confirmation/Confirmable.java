package pl.subtelny.core.api.confirmation;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Confirmable {

    boolean canConfirm(Player player);

}
