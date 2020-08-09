package pl.subtelny.gui.session;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.entity.Player;
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.repository.Storage;

public class PlayerCrateSessionStorage extends Storage<Player, PlayerCrateSession> {

    public PlayerCrateSessionStorage() {
        super(Caffeine.newBuilder().build());
    }

}
