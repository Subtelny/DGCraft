package pl.subtelny.gui.session;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.entity.Player;
import pl.subtelny.repository.Storage;

public class CrateSessionStorage extends Storage<Player, PlayerCrateSession> {

    public CrateSessionStorage() {
        super(Caffeine.newBuilder().build());
    }

}
