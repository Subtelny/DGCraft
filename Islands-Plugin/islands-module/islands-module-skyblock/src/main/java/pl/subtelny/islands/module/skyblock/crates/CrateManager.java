package pl.subtelny.islands.module.skyblock.crates;

import org.bukkit.entity.Player;
import pl.subtelny.islands.module.skyblock.model.SkyblockIsland;

public interface CrateManager {

    void open(Player player);

    void open(Player player, SkyblockIsland island);

    void reload();

}
