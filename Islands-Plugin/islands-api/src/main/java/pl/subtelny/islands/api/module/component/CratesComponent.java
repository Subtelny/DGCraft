package pl.subtelny.islands.api.module.component;

import org.bukkit.entity.Player;
import pl.subtelny.islands.api.IslandId;

public interface CratesComponent extends IslandComponent {

    void openCrate(Player player, String crateName);

    void openCrate(Player player, IslandId islandId, String crate);

    void reload();

}
